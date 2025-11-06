package controller.cart;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart; // Thêm import này

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        // 1. Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");

        // 2. Lấy thông tin người dùng từ session
        // (Giả sử khi đăng nhập bạn lưu User/Account vào session với tên là "account")
        Object acc = session.getAttribute("account");

        // 3. Kiểm tra Giỏ hàng trước
        // Nếu giỏ hàng rỗng, không cho checkout, đá về trang giỏ hàng
        if (cart == null || cart.getItems().isEmpty()) {
            // (Tùy chọn) Thêm thông báo lỗi
            request.setAttribute("errorMsg", "Giỏ hàng của bạn đang trống!");
            // Chuyển hướng về trang giỏ hàng
            request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
            return; // Dừng servlet
        }

        // 4. Kiểm tra Đăng nhập
        if (acc == null) {
            // Nếu người dùng CHƯA đăng nhập
            // Lưu lại trang "checkout" để sau khi đăng nhập thì quay lại
            session.setAttribute("url", "checkout"); 
            // Đẩy (redirect) về trang đăng nhập
            response.sendRedirect("login");
        } else {
            // Nếu người dùng ĐÃ đăng nhập
            // Chuyển (forward) họ đến trang điền thông tin thanh toán
            request.getRequestDispatcher("cart/checkout.jsp").forward(request, response);
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