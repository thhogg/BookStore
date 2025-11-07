package controller.admin;

import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Order;

@WebServlet(name = "ManageOrdersServlet", urlPatterns = {"/admin/manage-orders"})
public class ManageOrdersServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            OrderDAO orderDAO = OrderDAO.getInstance();
            
            // 1. Lấy tham số search và sort
            String searchKey = request.getParameter("searchKey");
            String sortBy = request.getParameter("sortBy");

            // 2. Gọi hàm DAO mới (thay thế cho getAllOrders())
            List<Order> allOrders = orderDAO.getAdminOrders(searchKey, sortBy);

            // 3. Gửi danh sách và các tham số tìm kiếm/sắp xếp sang JSP
            request.setAttribute("allOrders", allOrders);
            request.setAttribute("searchKey", searchKey);
            request.setAttribute("sortBy", sortBy);

            // 4. Chuyển tiếp (forward) đến trang JSP (dùng / để đảm bảo đúng)
            request.getRequestDispatcher("/admin/manage-orders.jsp").forward(request, response);

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