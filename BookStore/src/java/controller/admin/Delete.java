/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.BookDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 *
 * @author Leo
 */
public class Delete extends HttpServlet {
    
    private BookDAO bookDao;
    private UserDAO userDao;
    
    @Override
    public void init() {
        bookDao = BookDAO.getInstance();
        userDao = UserDAO.getInstance();
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String type = request.getParameter("type");
        String idStr = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println(e);
        } 
        
        if (type.equals("book")) {
            bookDao.deleteById(id);
            request.setAttribute("message", "Delete book successfully!");
            request.getRequestDispatcher("books").forward(request, response);
        }
        
        if (type.equals("user")) {
            userDao.deleteById(id);
            request.setAttribute("message", "Delete user successfully!");
            request.getRequestDispatcher("users").forward(request, response);
        }
    } 

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

}
