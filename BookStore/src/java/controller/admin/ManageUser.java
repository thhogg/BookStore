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
import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import model.User;

/**
 *
 * @author Leo
 */
public class ManageUser extends HttpServlet {

    private UserDAO userDao;

    @Override
    public void init() {
        userDao = UserDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users = userDao.getAllUsers();
        request.setAttribute("users", users);

        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }

        request.getRequestDispatcher("manage-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        List<User> users = userDao.getAllUsers(); // Giả sử lấy danh sách user

        // Search
        if (search != null && !search.trim().isEmpty()) {
            String normalizedSearch = normalizeText(search.trim());
            users.removeIf(user -> {
                String normalizedFullName = normalizeText(user.getFullname());
                String normalizedUsername = normalizeText(user.getUserName());
                String normalizedEmail = normalizeText(user.getEmail());
                String normalizedRole = normalizeText(user.getRole());

                return !(normalizedFullName.contains(normalizedSearch)
                        || normalizedUsername.contains(normalizedSearch)
                        || normalizedEmail.contains(normalizedSearch)
                        || normalizedRole.contains(normalizedSearch));
            });
        }

        // Sort
        if (sort != null) {
            switch (sort) {
                case "fullname_asc" ->
                    users.sort(Comparator.comparing(User::getFullname, String.CASE_INSENSITIVE_ORDER));
                case "fullname_desc" ->
                    users.sort(Comparator.comparing(User::getFullname, String.CASE_INSENSITIVE_ORDER).reversed());
                case "username_asc" ->
                    users.sort(Comparator.comparing(User::getUserName, String.CASE_INSENSITIVE_ORDER));
                case "username_desc" ->
                    users.sort(Comparator.comparing(User::getUserName, String.CASE_INSENSITIVE_ORDER).reversed());
                case "email_asc" ->
                    users.sort(Comparator.comparing(User::getEmail, String.CASE_INSENSITIVE_ORDER));
                case "email_desc" ->
                    users.sort(Comparator.comparing(User::getEmail, String.CASE_INSENSITIVE_ORDER).reversed());
                case "role_asc" ->
                    users.sort(Comparator.comparing(User::getRole, String.CASE_INSENSITIVE_ORDER));
                case "role_desc" ->
                    users.sort(Comparator.comparing(User::getRole, String.CASE_INSENSITIVE_ORDER).reversed());
                case "registered_asc" ->
                    users.sort(Comparator.comparing(User::getCreatedAt));
                case "registered_desc" ->
                    users.sort(Comparator.comparing(User::getCreatedAt).reversed());
                default -> {
                    
                }
            }
        }

        request.setAttribute("users", users);
        request.setAttribute("search", search);
        request.setAttribute("sort", sort);
        request.getRequestDispatcher("manage-user.jsp").forward(request, response);
    }

    private static String normalizeText(String str) {
        if (str == null) {
            return "";
        }
        // Chuẩn hóa Unicode (NFD)
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        // Xóa các ký tự dấu
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        // Chuyển đ, Đ thành d, D
        temp = temp.replace('đ', 'd').replace('Đ', 'D');
        // Chuyển về chữ thường
        temp = temp.toLowerCase();
        // Chuẩn hóa khoảng trắng
        temp = temp.replaceAll("\\s+", " ").trim();
        return temp;
    }

}
