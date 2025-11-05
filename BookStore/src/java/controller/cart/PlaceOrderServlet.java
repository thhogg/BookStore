package controller.cart;

import static java.lang.Math.round;
import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date; // Quan trọng: import java.sql.Date
import model.Cart;
import model.Item;
import model.Order;
import model.OrderDetail;
import model.User; // Hoặc model.Account, tùy bạn đặt tên là gì

@WebServlet(name = "PlaceOrderServlet", urlPatterns = {"/place-order"})
public class PlaceOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        // 1. KIỂM TRA ĐIỀU KIỆN
        // Lấy giỏ hàng và tài khoản từ session
        Cart cart = (Cart) session.getAttribute("cart");
        User account = (User) session.getAttribute("account"); // Sửa "User" thành "Account" nếu tên class của bạn là Account

        // Nếu 1 trong 2 không tồn tại, đá về trang chủ
        if (cart == null || cart.getItems().isEmpty() || account == null) {
            response.sendRedirect("index.jsp"); // Hoặc login.jsp
            return;
        }

        try {
            // 2. LẤY THÔNG TIN TỪ FORM (checkout.jsp)
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            // Gộp thông tin lại cho cột ShippingAddress
            String shippingAddress = "Name: " + name + ", Phone: " + phone + ", Address: " + address;

            // 3. TẠO ĐỐI TƯỢNG 'Order'
            // Lấy ngày hiện tại
            long millis = System.currentTimeMillis();
            Date orderDate = new Date(millis);

            String paymentMethod = "COD"; // Thay vì null
            String status = "Pending";    // Thay vì null

            Order order = new Order();
            order.setUserID(account.getUserID());
            order.setOrderDate(orderDate);
            order.setTotalAmount((int) Math.round(cart.getTotalMoney()));
            order.setPaymentMethod(paymentMethod);
            order.setStatus(status);
            order.setShippingAddress(shippingAddress);

            // 4. LƯU 'Order' VÀO DATABASE (dùng DAO)
            OrderDAO orderDAO = OrderDAO.getInstance();
            int orderId = orderDAO.addOrder(order); // Hàm này trả về OrderID vừa tạo

            // 5. KIỂM TRA LƯU ORDER
            if (orderId == -1) {
                // Có lỗi xảy ra khi lưu Order
                request.setAttribute("errorMsg", "Không thể xử lý đơn hàng của bạn. Vui lòng thử lại!");
                request.getRequestDispatcher("cart/checkout.jsp").forward(request, response);
                return;
            }

            // 6. LƯU 'OrderDetail' VÀO DATABASE (lặp qua giỏ hàng)
            // Nếu Order đã lưu thành công (orderId > 0)
            for (Item item : cart.getItems()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderID(orderId);
                detail.setBookID(item.getBook().getBookID());
                detail.setQuantity(item.getQuantity());
                detail.setUnitPrice(item.getBook().getPrice()); // Lấy giá sách (int)

                // Gọi DAO để lưu chi tiết đơn hàng
                orderDAO.addOrderDetail(detail);
            }

            // 7. DỌN DẸP VÀ CHUYỂN HƯỚNG
            // Xóa giỏ hàng khỏi session
            session.removeAttribute("cart");

            // Chuyển hướng đến trang cảm ơn
            response.sendRedirect("cart/thankyou.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Đã xảy ra lỗi. Vui lòng thử lại!");
            request.getRequestDispatcher("cart/checkout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Không cho phép truy cập URL này bằng GET.
        // Chuyển hướng người dùng về trang giỏ hàng.
        response.sendRedirect("cart/cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Chỉ phương thức POST (từ form checkout) mới được xử lý
        processRequest(request, response);
    }
}
