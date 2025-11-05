package controller.cart; // Hoặc package controller.client, controller.shop...

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
        
        String bookIdStr = request.getParameter("bookId");
        String quantityStr = request.getParameter("quantity");
        
        // --- SỬA LỖI Ở ĐÂY ---
        // Thêm "lá chắn" để kiểm tra NULL hoặc RỖNG
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            
            // Nếu không có bookId, đây là 1 request lỗi.
            // (In ra lỗi để chúng ta biết)
            System.err.println("AddToCartServlet: bookIdStr bị null hoặc rỗng!");
            
            // Không làm gì cả và đưa người dùng về trang shop
            response.sendRedirect("test-shop.jsp");
            
            return; // Dừng hàm ngay lập tức
        }
        // --- KẾT THÚC SỬA LỖI ---
        
        // Code bên dưới chỉ chạy khi bookIdStr CHẮC CHẮN CÓ GIÁ TRỊ
        
        try {
            int bookId = Integer.parseInt(bookIdStr); // Dòng 27 cũ (bây giờ đã an toàn)
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
                session.setAttribute("successMsg", "Đã thêm sản phẩm vào giỏ hàng!");
            }

        } catch (NumberFormatException e) {
            // Trường hợp này xảy ra nếu bookIdStr không phải là null,
            // mà là một chữ (ví dụ: ?bookId=abc)
            e.printStackTrace();
            response.sendRedirect("test-shop.jsp"); // Cũng đưa về trang shop
            return; // Dừng lại
        }
        
        // 7. Chuyển hướng khi THÊM THÀNH CÔNG
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