/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package homeController;

import dal.BookDAO;
import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.Category;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchControll", urlPatterns = {"/search"})
public class SearchControll extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtSearch = request.getParameter("txt");
    if (txtSearch != null) {
        txtSearch = txtSearch.trim();
    }
    // xu ly dau vao
    List<Book> list;
    if (txtSearch == null || txtSearch.isEmpty()) {
        // Không có từ khóa → hiển thị tất cả (hoặc trang chủ mặc định)
        list = BookDAO.getInstance().getAllBooks();
    } else {
        list = BookDAO.getInstance().searchByName(txtSearch);
    }

    // check khi null
    if (list == null) {
        list = new ArrayList<>();
    }
    
    BookDAO dao = BookDAO.getInstance();
    Book news = dao.getNew();
    CategoryDAO dao1 = CategoryDAO.getInstance();
    List<Category> list1 = dao1.getAllCategories();
    

    // gui data to jsp
    request.setAttribute("listB", list);
    request.setAttribute("txtS", txtSearch); // giữ lại từ khóa trong ô tìm kiếm
    request.setAttribute("n", news);
    request.setAttribute("listC", list1);

    
    request.getRequestDispatcher("views/home.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
