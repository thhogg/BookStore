package controller.admin;

import dal.BookDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // Cần thiết để mapping URL
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/admin/dashboard"}) // Thêm mapping URL cho Controller
public class DashboardController extends HttpServlet {
    
    // Khai báo field để lưu instance của BookDAO
    private BookDAO bookDAO; 

    @Override
    public void init() throws ServletException {
        // Khởi tạo BookDAO MỘT LẦN DUY NHẤT khi Servlet được tải
        // Nếu BookDAO là Singleton, nên dùng: bookDAO = BookDAO.getInstance();
        bookDAO = BookDAO.getInstance(); 
    } // <--- KHẮC PHỤC LỖI THIẾU DẤU ĐÓNG NGOẶC NHỌN

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // SỬ DỤNG INSTANCE ĐÃ KHỞI TẠO TRONG init()
            // 1. Lấy dữ liệu
            int totalBooks = bookDAO.countAllBooks(); 
            
            // 2. Gửi dữ liệu sang View (dashboard.jsp)
            request.setAttribute("totalBooks", totalBooks);
            
            // 3. Chuyển hướng
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi: Chuyển hướng đến trang lỗi
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải Dashboard.");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
