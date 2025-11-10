package controller.admin;

import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 1. Đặt URL khớp với link trong JSP
@WebServlet(name = "DeleteOrderServlet", urlPatterns = {"/admin/delete-order"})
public class DeleteOrderServlet extends HttpServlet {

    // Dùng doGet vì chúng ta dùng link <a> (hoặc POST nếu bạn muốn an toàn hơn)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String orderIdStr = request.getParameter("orderId");
        String contextPath = request.getContextPath();

        try {
            int orderId = Integer.parseInt(orderIdStr);
            OrderDAO orderDAO = OrderDAO.getInstance();
            
            // 2. Gọi hàm xóa (sẽ tạo ở bước 3)
            boolean success = orderDAO.deleteOrder(orderId);

            if (success) {
                // Nhờ CSDL của bạn có "ON DELETE CASCADE", 
                // các chi tiết đơn hàng (OrderDetail) cũng tự động bị xóa theo.
                request.getSession().setAttribute("successMsg", "Đã xóa vĩnh viễn đơn hàng #" + orderId + ".");
            } else {
                request.getSession().setAttribute("errorMsg", "Xóa đơn hàng #" + orderId + " thất bại.");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "OrderID không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Đã xảy ra lỗi: " + e.getMessage());
        }

        // 3. Chuyển hướng Admin quay lại trang quản lý
        response.sendRedirect(contextPath + "/admin/manage-orders");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}