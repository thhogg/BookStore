package controller.cart; // Nhớ đảm bảo tên package có 's'

import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Order;
import model.User; // Hoặc model.Account, tùy bạn đặt tên là gì

@WebServlet(name = "MyOrdersServlet", urlPatterns = {"/my-orders"})
public class MyOrdersServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();

        // 1. Kiểm tra đăng nhập
        User account = (User) session.getAttribute("acc"); 

        // Nếu chưa đăng nhập, đá về trang login
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 2. Lấy UserID từ tài khoản
            int userID = account.getUserName(); // Giả sử model User/Account có hàm getUserID()

            // 3. Gọi DAO để lấy danh sách đơn hàng
            OrderDAO orderDAO = OrderDAO.getInstance();
            List<Order> orderList = orderDAO.getOrdersByUserID(userID);

            // 4. Gửi danh sách đơn hàng sang trang JSP
            request.setAttribute("orderList", orderList);

            // 5. Chuyển hướng (forward) sang trang "my-orders.jsp"
            // (Chúng ta sẽ tạo file này ở bước tiếp theo)
            request.getRequestDispatcher("cart/my-orders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi
            response.sendRedirect("index.jsp");
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