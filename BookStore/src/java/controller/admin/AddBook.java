/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.BookDAO;
import dal.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import model.Book;
import model.Category;

/**
 *
 * @author Leo
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB - lưu tạm trong RAM
        maxFileSize = 1024 * 1024 * 10, // 10MB - giới hạn 1 file
        maxRequestSize = 1024 * 1024 * 50 // 50MB - giới hạn tổng request
)

public class AddBook extends HttpServlet {

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
        List<Category> categories = categoryDao.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("add-book.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String categoryIdStr = request.getParameter("category");
        String isbn = request.getParameter("isbn");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");
        String description = request.getParameter("description");
     
        int price = 0;
        int stock = 0;
        int categoryId = 0;
        try {
            price = Integer.parseInt(priceStr);
            stock = Integer.parseInt(stockStr);
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
              
        //Get dir-upload from web.xml
        String uploadDir = request.getServletContext().getInitParameter("dir-upload");
        
        Part imagePart = request.getPart("imageUrl");
        String imageName = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            
            String submittedFileName = imagePart.getSubmittedFileName();
            
            imageName = System.currentTimeMillis() + "_" + submittedFileName;

            String uploadPath = getServletContext().getRealPath("") + uploadDir;          
            File uploads = new File(uploadPath);
            if (!uploads.exists()) {
                uploads.mkdirs();
            }

            imagePart.write(uploadPath+File.separator+imageName);
        }
        
        String imageUrl = uploadDir+File.separator+imageName;
        
        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(author);
        b.setPublisher(publisher);
        b.setCategoryID(categoryId);
        b.setPrice(price);
        b.setStock(stock);
        b.setIsbn(isbn);
        b.setDescription(description);
        b.setImageUrl(imageUrl);
        b.setCreatedAt();
        
        bookDao.insertBook(b);
        HttpSession session = request.getSession();
        session.setAttribute("message", "Add book successfully!");
        response.sendRedirect("books");
    }

}
