/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.BookDAO;
import dal.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.Normalizer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import model.Book;

/**
 *
 * @author Leo
 */
public class ManageBook extends HttpServlet {

    private BookDAO bookDao;
    private CategoryDAO categoryDao;

    @Override
    public void init() {
        bookDao = BookDAO.getInstance();
        categoryDao = CategoryDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookDao.getAllBooks();
        request.setAttribute("books", books);
        request.setAttribute("categoryDao", categoryDao);

        HttpSession session = request.getSession();
        String successMessage = (String) session.getAttribute("successMessage");
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        request.getRequestDispatcher("manage-book.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        List<Book> books = bookDao.getAllBooks();

        // Search
        if (search != null && !search.trim().isEmpty()) {
            String normalizedSearch = normalizeText(search.trim());
            books.removeIf(book -> {
                String normalizedTitle = book.getTitle() == null ? "" : normalizeText(book.getTitle());
                String normalizedAuthor = book.getAuthor() == null ? "" : normalizeText(book.getAuthor());
                String normalizedDescription = book.getDescription() == null ? "" : normalizeText(book.getDescription());

                return !(normalizedTitle.contains(normalizedSearch)
                        || normalizedAuthor.contains(normalizedSearch)
                        || normalizedDescription.contains(normalizedSearch));
            });
        }

        //Sort
        if (sort != null) {
            switch (sort) {
                case "title_asc" ->
                    Collections.sort(books, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
                case "title_desc" ->
                    Collections.sort(books, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());
                case "price_asc" ->
                    Collections.sort(books, Comparator.comparingDouble(Book::getPrice));
                case "price_desc" ->
                    Collections.sort(books, Comparator.comparingDouble(Book::getPrice).reversed());
                case "stock_asc" ->
                    Collections.sort(books, Comparator.comparingInt(Book::getStock));
                case "stock_desc" ->
                    Collections.sort(books, Comparator.comparingInt(Book::getStock).reversed());
                default -> {
                }
            }

        }

        request.setAttribute("books", books);
        request.setAttribute("categoryDao", categoryDao);

        request.setAttribute("search", search);
        request.setAttribute("sort", sort);

        request.getRequestDispatcher("manage-book.jsp").forward(request, response);

    }

    private static String normalizeText(String str) {
        if (str == null) {
            return "";
        }
        // Chuẩn hóa Unicode (NFD)
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        // Xóa các ký tự dấu
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        // Chuyển đ, Đ thành d, D
        temp = temp.replace('đ', 'd').replace('Đ', 'D');
        // Chuyển về chữ thường
        temp = temp.toLowerCase();
        // Chuẩn hóa khoảng trắng
        temp = temp.replaceAll("\\s+", " ").trim();
        return temp;
    }

}
