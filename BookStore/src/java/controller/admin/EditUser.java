/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author Leo
 */
public class EditUser extends HttpServlet {

    private UserDAO userDao;

    @Override
    public void init() {
        userDao = UserDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        String username = request.getParameter("username");

        if (type != null && username != null && type.equals("edit")) {

            User u = userDao.getUserByUserName(username);
            if (u != null) {
                request.setAttribute("editedUser", u);
            } else {
                request.setAttribute("errorMessage", "User not found for editing");
            }

        }

        request.getRequestDispatcher("edit-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String type = request.getParameter("type");
        String userName = request.getParameter("userName");
        String pass = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String role = request.getParameter("role");

        if (type != null && type.equals("add")) {
            try {
                int rowInserted = userDao.insertUser(userName, pass, fullname, email, phone, address, role);
                if (rowInserted > 0) {
                    session.setAttribute("successMessage", "Add user successfully! Username: " + userName);
                } else {
                    session.setAttribute("errorMessage", "Add user failed! Unexpectedly no rows were inserted.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                String errorMsg = "Add user failed! User already exists or database error occurred.";
                if (e.getMessage().contains("UNIQUE KEY constraint")) {
                    errorMsg = "Add user failed! The Username or Email you entered already exists.";
                }
                session.setAttribute("errorMessage", errorMsg);
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", "Add user failed! An unexpected system error occurred: " + e.getMessage());
            }

        }

        if (type != null && type.equals("edit")) {

            User u = userDao.getUserByUserName(userName);
            if (u != null) {
                u.setUserName(userName);
                u.setPassword(pass);
                u.setFullname(fullname);
                u.setEmail(email);
                u.setPhone(phone);
                u.setAddress(address);
                u.setRole(role);
                try {
                    int rowUpdated = userDao.updateUser(userName, u);
                    if (rowUpdated > 0) {
                        session.setAttribute("successMessage", "Update user successfully!");
                    } else {
                        session.setAttribute("errorMessage", "Update failed! No changes were applied.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "Update user failed! The new username/email may already exist or a database error occurred.");
                }

            } else {
                session.setAttribute("errorMessage", "Update failed! User '" + userName + "' not found.");
            }

        }

        response.sendRedirect("users");
    }

}
