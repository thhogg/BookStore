
package controller.admin;

import dal.BookDAO;
import dal.UserDAO;   // <-- Thêm import
import dal.OrderDAO;  // <-- Thêm import
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/admin/dashboard"})
public class DashboardController extends HttpServlet {
    
    // Khai báo các DAO
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo tất cả DAO
        bookDAO = BookDAO.getInstance();
        userDAO = UserDAO.getInstance(); // <-- Khởi tạo
        orderDAO = OrderDAO.getInstance(); // <-- Khởi tạo
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Lấy tất cả dữ liệu
            int totalBooks = bookDAO.countAllBooks();
            int totalUsers = userDAO.countAllUsers();       // <-- Gọi hàm
            int totalOrders = orderDAO.countAllOrders();     // <-- Gọi hàm
            double totalRevenue = orderDAO.getTotalRevenue(); // <-- Gọi hàm
            
            // 2. Gửi tất cả sang View (dashboard.jsp)
            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("totalRevenue", totalRevenue);
            
            // 3. Chuyển hướng
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải Dashboard.");
        }
    }
}

