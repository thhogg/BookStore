package controller.cart;

import dal.BookDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;
import model.Cart;
import model.Item;

@WebServlet(name = "AddToCartServlet", urlPatterns = {"/add-to-cart"})
public class AddToCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String contextPath = request.getContextPath();
        String bookIdStr = request.getParameter("bookId");
        String quantityStr = request.getParameter("quantity");

        if (bookIdStr == null || bookIdStr.isEmpty()) {
            System.err.println("AddToCartServlet: bookId bị null hoặc rỗng!");
            response.sendRedirect(contextPath + "/home");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdStr);
            int quantity = 1;
            if (quantityStr != null && !quantityStr.isEmpty()) {
                quantity = Integer.parseInt(quantityStr);
            }

            BookDAO bookDAO = BookDAO.getInstance();
            Book book = bookDAO.getBookByBookID(bookId);

            if (book != null) {
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");

                if (cart == null) {
                    cart = new Cart();
                }

                Item newItem = new Item(book, quantity);
                cart.addItem(newItem);
                session.setAttribute("cart", cart);
                // nếu trang "home" của bạn có code để đọc ${sessionScope.successMsg}
                session.setAttribute("successMsg", "Đã thêm sản phẩm vào giỏ hàng!");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(contextPath + "/home");
            return;
        }

        // 4. Chuyển hướng khi THÊM THÀNH CÔNG
        // Thay vì chuyển đến /cart/cart.jsp, chúng ta chuyển về trang trước đó
        // Lấy trang mà người dùng vừa ở (ví dụ: /home, /detail.jsp)
        String referer = request.getHeader("Referer");

        String redirect = request.getParameter("redirect");
        if ("cart".equals(redirect)) {
            response.sendRedirect(contextPath + "/cart/cart.jsp");
            return;
        }

        // Chuyển hướng HỌ TRỞ LẠI trang đó
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            // Dự phòng nếu không tìm thấy "Referer"
            response.sendRedirect(contextPath + "/home");
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
