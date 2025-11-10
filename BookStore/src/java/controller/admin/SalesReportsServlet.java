package controller.admin;

import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
// 1. Import model mới
import model.DetailedSalesReport; 

@WebServlet(name = "SalesReportsServlet", urlPatterns = {"/admin/sales-reports"})
public class SalesReportsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 2. Lấy tham số search và sort
            String searchKey = request.getParameter("searchKey");
            String sortBy = request.getParameter("sortBy");

            OrderDAO orderDAO = OrderDAO.getInstance();
            
            // 3. Gọi hàm DAO mới
            List<DetailedSalesReport> reportData = orderDAO.getDetailedSalesReport(searchKey, sortBy);

            // 4. Gửi dữ liệu mới sang JSP
            request.setAttribute("reportData", reportData);
            
            // 5. Gửi lại tham số để hiển thị trên form
            request.setAttribute("searchKey", searchKey);
            request.setAttribute("sortBy", sortBy);

            // 6. Chuyển tiếp (forward) đến trang JSP
            request.getRequestDispatcher("/admin/sales-reports.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải báo cáo.");
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