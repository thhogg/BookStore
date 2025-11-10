/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package homeController;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginControll", urlPatterns = {"/login"})
public class LoginControll extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập đầy đủ!");
            request.setAttribute("username", username);
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }
        
        username = username.trim();

        User user = UserDAO.getInstance().checkLogin(username.trim(), password);

        HttpSession session = request.getSession();
        session.setAttribute("acc", user);
        
        if (user == null) {
            request.setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        } else {
            session.setAttribute("acc", user);
            if (user.getRole().equals("Admin")) {
                response.sendRedirect("admin/dashboard.jsp");
            } else if (user.getRole().equals("Customer")) {
                response.sendRedirect("home");
            }
        }

        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
