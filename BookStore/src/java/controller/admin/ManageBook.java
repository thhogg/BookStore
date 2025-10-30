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
import java.util.List;
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
        request.setAttribute("books",books);
        request.setAttribute("categoryDao",categoryDao);
        request.getRequestDispatcher("manage-book.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

   
}
