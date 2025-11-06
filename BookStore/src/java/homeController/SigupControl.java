/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package homeController;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SigupControl", urlPatterns = {"/sigup"})
public class SigupControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repass = request.getParameter("repass");
        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        if (username == null || username.trim().isEmpty()
                || password == null || repass == null) {
            request.setAttribute("message", "Vui lòng nhập đầy đủ!");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }

        if (!password.equals(repass)) {
            request.setAttribute("message", "Mật khẩu không khớp!");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }

        UserDAO dao = UserDAO.getInstance();
        if (dao.isUsernameExist(username.trim())) {
            request.setAttribute("message", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }

        boolean success = dao.register(username.trim(), password, fullName, email, phone, address);
        request.setAttribute("message", success
                ? "Đăng ký thành công! Vui lòng đăng nhập."
                : "Đăng ký thất bại!");

        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
