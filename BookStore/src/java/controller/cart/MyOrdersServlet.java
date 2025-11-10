package controller.cart; // Nhớ đảm bảo tên package có 's'

import dal.OrderDAO;
import dal.UserDAO;
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

        if (account == null) {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "login"); // Thêm /login.jsp
            return;
        }

        try {
            // 2. Lấy UserName (String) từ tài khoản
            // Giả sử model User của bạn có hàm getUserName() trả về String
            String userName = account.getUserName(); 

            // 3. Gọi DAO để lấy danh sách đơn hàng
            OrderDAO orderDAO = OrderDAO.getInstance();
            
            // TẠO PHƯƠNG THỨC NÀY TRONG OrderDAO (xem hướng dẫn bên dưới)
            List<Order> orderList = orderDAO.getOrdersByUserName(userName);

            // 4. Gửi danh sách đơn hàng sang trang JSP
            request.setAttribute("orderList", orderList);

            // 5. Chuyển hướng (forward) sang trang "my-orders.jsp"
            request.getRequestDispatcher("cart/my-orders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi
            String contextPath = request.getContextPath(); // Thêm contextPath
            response.sendRedirect(contextPath + "/index.jsp"); // Thêm /index.jsp
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
    public static void main(String[] args) {
        // 1. Nhập username + password để test
        String username = "user1";    // thay bằng username trong DB
        String password = "123456";      // thay bằng password tương ứng

        // 2. Gọi hàm checkLogin
        User user = UserDAO.getInstance().checkLogin(username, password);

        // 3. Kiểm tra kết quả
        if (user == null) {
            System.out.println("❌ Sai tên đăng nhập hoặc mật khẩu!");
        } else {
            System.out.println("✅ Đăng nhập thành công!");
            System.out.println("Tên đăng nhập: " + user.getUserName());
            System.out.println("Vai trò: " + user.getRole());
        }
    }
}