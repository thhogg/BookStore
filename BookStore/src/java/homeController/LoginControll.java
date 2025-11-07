/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package homeController;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            String cuser = null, cpass = null;
//            for (Cookie c : cookies) {
//                if ("cuser".equals(c.getName())) {
//                    cuser = c.getValue();
//                }
//                if ("cpass".equals(c.getName())) {
//                    cpass = c.getValue();
//                }
//            }
//            if (cuser != null && cpass != null) {
//                request.setAttribute("username", cuser);
//                request.setAttribute("password", cpass);
//                request.setAttribute("remember", "1");
//            }
//        }
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
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }

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
                response.sendRedirect("admin/add-book.jsp");
            } else if (user.getRole().equals("Customer")) {
                response.sendRedirect("home");
            }
        }

        //  String remember = request.getParameter("remember");
        //     if ("1".equals(remember)) {
//            Cookie cUser = new Cookie("cuser", username);
//            Cookie cPass = new Cookie("cpass", password);
//            cUser.setMaxAge(7 * 24 * 60 * 60);
//            cPass.setMaxAge(7 * 24 * 60 * 60);
//            response.addCookie(cUser);
//            response.addCookie(cPass);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
