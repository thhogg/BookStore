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

// SỬA 1: Import java.sql.Timestamp thay vì java.sql.Date
import java.sql.Timestamp; 

import model.Cart;
import model.Item;
import model.Order;
import model.OrderDetail;
import model.User; // Hoặc model.Account

@WebServlet(name = "PlaceOrderServlet", urlPatterns = {"/place-order"})
public class PlaceOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        // 1. KIỂM TRA ĐIỀU KIỆN
        Cart cart = (Cart) session.getAttribute("cart");
                User account = (User) session.getAttribute("acc"); 

        // Nếu 1 trong 2 không tồn tại, đá về trang login
        if (cart == null || cart.getItems().isEmpty() || account == null) {
            response.sendRedirect("login"); // Nên chuyển về login.jsp
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
            // Lấy ngày giờ hiện tại
            long millis = System.currentTimeMillis();
            Timestamp orderDate = new Timestamp(millis); 

            String paymentMethod = "COD"; // Giả sử mặc định là COD
            String status = "Pending";    // Trạng thái ban đầu

            Order order = new Order();
            
            // SỬA 4: Dùng setUserName(String) thay vì setUserID(int)
            order.setUserName(account.getUserName());
            
            order.setOrderDate(orderDate); // Truyền đối tượng Timestamp
            order.setTotalAmount((int) Math.round(cart.getTotalMoney()));
            order.setPaymentMethod(paymentMethod);
            order.setStatus(status);
            order.setShippingAddress(shippingAddress);

            // 4. LƯU 'Order' VÀO DATABASE (dùng DAO)
            OrderDAO orderDAO = OrderDAO.getInstance();
            // Hàm addOrder này đã được sửa ở bước trước để nhận UserName và Timestamp
            int orderId = orderDAO.addOrder(order); 

            // 5. KIỂM TRA LƯU ORDER
            if (orderId == -1) {
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
                detail.setUnitPrice(item.getBook().getPrice()); // Lấy giá sách

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