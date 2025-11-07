package controller.admin;

import dal.BookDAO; // 1. Import BookDAO
import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List; // 2. Import List
import model.OrderDetail; // 3. Import OrderDetail

@WebServlet(name = "UpdateOrderStatusServlet", urlPatterns = {"/admin/update-order-status"})
public class UpdateOrderStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String orderIdStr = request.getParameter("orderId");
        String newStatus = request.getParameter("newStatus");

        if (orderIdStr == null || newStatus == null || newStatus.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage-orders");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            OrderDAO orderDAO = OrderDAO.getInstance();
            BookDAO bookDAO = BookDAO.getInstance(); // 4. Khởi tạo BookDAO

            // 5. Lấy trạng thái CŨ (rất quan trọng)
            String oldStatus = orderDAO.getOrderStatusById(orderId);

            // 6. Cập nhật trạng thái mới
            boolean success = orderDAO.updateOrderStatus(orderId, newStatus);

            if (success && !oldStatus.equals(newStatus)) {
                
                // 7. LẤY CHI TIẾT ĐƠN HÀNG
                List<OrderDetail> details = orderDAO.getOrderDetailsByOrderID(orderId);
                
                // 8. LOGIC TRỪ KHO (KHI HOÀN THÀNH)
                // Chỉ trừ khi chuyển từ trạng thái "chưa trừ" (Pending, Shipped) sang "Completed"
                if (newStatus.equals("Completed")) {
                    // (Giả sử bạn trừ kho khi "Hoàn thành")
                    for (OrderDetail detail : details) {
                        bookDAO.decreaseStock(detail.getBookID(), detail.getQuantity());
                    }
                    request.getSession().setAttribute("successMsg", "Đã cập nhật đơn hàng #" + orderId + " & Trừ kho thành công!");
                
                // 9. LOGIC HOÀN KHO (KHI HỦY)
                // Chỉ hoàn kho nếu trạng thái trước đó KHÔNG PHẢI là "Cancelled"
                } else if (newStatus.equals("Cancelled") && !oldStatus.equals("Cancelled")) {
                    for (OrderDetail detail : details) {
                        bookDAO.increaseStock(detail.getBookID(), detail.getQuantity());
                    }
                    request.getSession().setAttribute("successMsg", "Đã hủy đơn hàng #" + orderId + " & Hoàn kho thành công!");
                
                } else {
                     request.getSession().setAttribute("successMsg", "Cập nhật đơn hàng #" + orderId + " thành công!");
                }
            } else if (!success) {
                 request.getSession().setAttribute("errorMsg", "Cập nhật đơn hàng #" + orderId + " thất bại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Đã xảy ra lỗi: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/manage-orders");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/manage-orders");
    }
}