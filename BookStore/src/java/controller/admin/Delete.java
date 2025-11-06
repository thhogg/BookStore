/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.BookDAO;
import dal.CategoryDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import model.Book;


/**
 *
 * @author Leo
 */
public class Delete extends HttpServlet {
    
    private BookDAO bookDao;
    private UserDAO userDao;
    private CategoryDAO cateogoryDao;
    
    @Override
    public void init() {
        bookDao = BookDAO.getInstance();
        userDao = UserDAO.getInstance();
        cateogoryDao = CategoryDAO.getInstance();
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String type = request.getParameter("type");
            
        if (type.equals("book")) {
            
            int id = Integer.parseInt(request.getParameter("id"));
            
            //delete book image file
            Book b = bookDao.getBookByBookID(id);    
            String oldImagePath = getServletContext().getRealPath("") + b.getImageUrl();
            File oldImageFile = new File(oldImagePath);
            if (oldImageFile.exists()) {
                boolean deleted = oldImageFile.delete();
                if (!deleted) {
                    System.out.println("Could not delete old image: " + oldImagePath);
                }
            }
            bookDao.deleteById(id);
            
            request.setAttribute("message", "Delete book successfully!");
            request.getRequestDispatcher("books").forward(request, response);
        }
        
        if (type.equals("user")) {
            String userName = request.getParameter("username");
            userDao.deleteByUserName(userName);
            request.setAttribute("message", "Delete user successfully!");
            request.getRequestDispatcher("users").forward(request, response);
        }
        
        if (type.equals("category")) {
            
            int id = Integer.parseInt(request.getParameter("id"));
            
            cateogoryDao.deleteById(id);
            request.setAttribute("message", "Delete category successfully!");
            request.getRequestDispatcher("categories").forward(request, response);
        }
    } 

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

}
