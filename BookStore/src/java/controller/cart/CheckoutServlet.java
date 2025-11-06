package controller.cart;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    /**
     * Xử lý cả GET và POST.
     * 1. Kiểm tra giỏ hàng có rỗng không.
     * 2. Kiểm tra người dùng đã đăng nhập chưa.
     * 3. Nếu ổn, chuyển tiếp đến trang điền thông tin thanh toán.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        
        // Dòng debug để kiểm tra Session ID (giữ lại)
        System.out.println("DEBUG: Session ID trên CheckoutServlet = " + session.getId());

        // 1. Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");

        // 2. Kiểm tra Giỏ hàng trước
        // Nếu giỏ hàng rỗng, không cho checkout, forward về trang giỏ hàng
        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("errorMsg", "Giỏ hàng của bạn đang trống!");
            request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
            return; // Dừng servlet
        }

        // 3. Kiểm tra Đăng nhập
        User account = (User) session.getAttribute("acc");
        if (account == null) {
            // Nếu chưa đăng nhập, redirect về trang login (đã sửa đúng contextPath)
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login"); // Chuyển đến Login Servlet
            return;
        } else {
            // 4. THÀNH CÔNG: Đã đăng nhập VÀ có giỏ hàng
            
            // SỬA LỖI: Đẩy giỏ hàng vào requestScope
            // để file 'cart/checkout.jsp' có thể truy cập bằng ${cart}
            request.setAttribute("cart", cart); 
            
            // Chuyển (forward) đến trang điền thông tin thanh toán
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