package controller.cart;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

// Đặt URL mapping cho servlet
@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/remove-from-cart"})
public class RemoveFromCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // 1. Lấy BookID từ trang cart.jsp
        String bookIdStr = request.getParameter("bookId");
        
        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            try {
                int bookId = Integer.parseInt(bookIdStr);

                // 2. Lấy giỏ hàng từ Session
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");

                // 3. Kiểm tra xem giỏ hàng có tồn tại không
                if (cart != null) {
                    // Gọi hàm xóa item đã viết trong Cart.java
                    cart.removeItem(bookId);
                    
                    // Cập nhật lại giỏ hàng trong session (dù không bắt buộc
                    // vì cart là object reference, nhưng làm cho chắc chắn)
                    session.setAttribute("cart", cart);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Xử lý nếu bookId không phải là số
            }
        }

        // 4. Chuyển hướng người dùng quay LẠI trang giỏ hàng
        // (Giả sử bạn đã chuyển cart.jsp vào thư mục /cart/)
        response.sendRedirect("cart/cart.jsp");
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