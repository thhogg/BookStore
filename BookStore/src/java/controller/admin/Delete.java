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
import java.sql.SQLException;
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
            
            try {
                int id = Integer.parseInt(request.getParameter("id"));

                Book b = bookDao.getBookByBookID(id);

                if (b == null) {
                    request.setAttribute("errorMessage", "Delete failed! Book ID " + id + " not found.");
                } else {
                    String successMsg = "Delete book successfully!";

                    try {
                        //delete from DB
                        int rowsDeleted = bookDao.deleteById(id);
                        if (rowsDeleted > 0) {

                            //delete image
                            if (b.getImageUrl() != null && !b.getImageUrl().isEmpty()) {
                                String oldImagePath = getServletContext().getRealPath("") + b.getImageUrl();
                                File oldImageFile = new File(oldImagePath);
                                if (oldImageFile.exists()) {
                                    boolean deleted = oldImageFile.delete();
                                    if (!deleted) {
                                        System.out.println("Could not delete old image: " + oldImagePath);
                                        successMsg = "Delete book successfully! (Warning: Could not delete image file from server.)";
                                    }
                                }
                            }
                            request.setAttribute("successMessage", successMsg);

                        } else {
                            // deleteById trả về 0 (Sách không bị xóa dù tìm thấy)
                            request.setAttribute("errorMessage", "Delete failed! Book ID " + id + " unexpectedly not deleted.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        request.setAttribute("errorMessage", "Delete book failed! It may be referenced by other data");
                    }
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error: Invalid Book ID format.");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Delete book failed! An unexpected system error occurred: " + e.getMessage());
            }

            request.getRequestDispatcher("books").forward(request, response);
        }

        if (type.equals("user")) {
            try {
                String userName = request.getParameter("username");
                userDao.deleteByUserName(userName);
                request.setAttribute("message", "Delete user successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Delete user failed! Please check if the user exists or if there are related records.");
            }

            request.getRequestDispatcher("users").forward(request, response);
        }

        if (type.equals("category")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int rowsDeleted = cateogoryDao.deleteById(id);
                if (rowsDeleted > 0) {
                    request.setAttribute("successMessage", "Delete category successfully!");
                } else {
                    request.setAttribute("errorMessage", "Delete failed! Category ID " + id + " not found.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Error: Invalid Category ID format.");
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Delete category failed! This category may still contain books and cannot be deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Delete category failed due to an unexpected error.");
            }

            request.getRequestDispatcher("categories").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
