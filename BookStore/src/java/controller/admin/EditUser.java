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
                request.setAttribute("message", "User not found for editing");
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
            userDao.insertUser(userName, pass, fullname, email, phone, address, role);
            session.setAttribute("message", "Add user successfully!");
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
                userDao.updateUser(userName, u);
                session.setAttribute("message", "Update user successfully!");
            } else {
                session.setAttribute("message", "User not found for update");
            }

        }

        response.sendRedirect("users");
    }

}
