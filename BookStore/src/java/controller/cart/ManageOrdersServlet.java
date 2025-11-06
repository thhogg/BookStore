package controller.cart;

import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Order;

// 1. Đặt URL cho trang quản lý đơn hàng
@WebServlet(name = "ManageOrdersServlet", urlPatterns = {"/admin/manage-orders"})
public class ManageOrdersServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            OrderDAO orderDAO = OrderDAO.getInstance();
            
            // 2. Gọi hàm mới trong DAO để lấy TẤT CẢ đơn hàng
            // (Bạn phải thêm hàm này vào OrderDAO.java - xem bên dưới)
            List<Order> allOrders = orderDAO.getAllOrders();

            // 3. Gửi danh sách này sang file JSP
            request.setAttribute("allOrders", allOrders);

            // 4. Chuyển tiếp (forward) đến trang JSP để hiển thị
            request.getRequestDispatcher("manage-orders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu có
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}