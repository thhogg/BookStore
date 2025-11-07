package controller.admin;

import dal.BookDAO;
import dal.UserDAO;
import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List; // Import List
import model.CustomerRanking; // Import model Ranking
import model.BestSeller; // Import model BestSeller

@WebServlet(urlPatterns = {"/admin/dashboard"})
public class DashboardController extends HttpServlet {
    
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = BookDAO.getInstance();
        userDAO = UserDAO.getInstance(); 
        orderDAO = OrderDAO.getInstance(); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Lấy tất cả dữ liệu thống kê (Cũ)
            int totalBooks = bookDAO.countAllBooks();
            int totalUsers = userDAO.countAllUsers();
            int totalOrders = orderDAO.countAllOrders();
            long totalRevenue = orderDAO.getTotalRevenue(); // Sửa kiểu long
            
            // 2. Lấy dữ liệu thống kê (Mới)
            int totalBooksSold = orderDAO.countTotalBooksSold();
            int totalPurchasingCustomers = orderDAO.countPurchasingCustomers();
            List<CustomerRanking> customerRankings = orderDAO.getCustomerPurchaseRanking();
            
            // 3. THÊM MỚI: LẤY SÁCH BÁN CHẠY (TOP 5)
            List<BestSeller> bestSellers = orderDAO.getBestSellingBooks(5);

            // 4. Gửi tất cả sang View (dashboard.jsp)
            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalBooksSold", totalBooksSold);
            request.setAttribute("totalPurchasingCustomers", totalPurchasingCustomers);
            request.setAttribute("customerRankings", customerRankings);
            request.setAttribute("bestSellers", bestSellers); // <-- GỬI DATA MỚI
            
            // 5. Chuyển hướng (Dùng đường dẫn tuyệt đối)
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải Dashboard.");
        }
    }
}