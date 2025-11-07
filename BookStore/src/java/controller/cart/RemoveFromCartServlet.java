package controller.cart;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/remove-from-cart"})
public class RemoveFromCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String bookIdStr = request.getParameter("bookId");

        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            try {
                int bookId = Integer.parseInt(bookIdStr);

                // 1. Logic xóa 
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");

                if (cart != null) {
                    cart.removeItem(bookId);
                    session.setAttribute("cart", cart);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 2. Chuyển hướng người dùng quay LẠI trang giỏ hàng
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/cart/cart.jsp");
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