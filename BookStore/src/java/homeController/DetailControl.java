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
import java.util.List;
import model.Book;
import model.Category;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DetailControl", urlPatterns = {"/detail"})
public class DetailControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pidStr = request.getParameter("pid");
        int bID = 1;
        if (pidStr != null && !pidStr.trim().isEmpty()) {
            try {
                bID = Integer.parseInt(pidStr);
                System.out.println("bookID = " + bID);
            } catch (NumberFormatException e) {
                System.out.println("bookID invalied!" + bID + "mac dinh = 1");
            }
        }
        BookDAO dao = BookDAO.getInstance();
        Book b = dao.getBookByBookID(bID);
        List<Category> listC = CategoryDAO.getInstance().getAllCategories();
        Book news = dao.getNew();
        request.setAttribute("detail", b);
        request.setAttribute("listC", listC);
        request.setAttribute("n", news);
        request.getRequestDispatcher("views/detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
