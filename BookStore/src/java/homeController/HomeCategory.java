package homeController;

import dal.BookDAO;
import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Book;
import model.Category;

/**
 *
 * @author Admin
 */
@WebServlet(name = "HomeCategory", urlPatterns = {"/category"})
public class HomeCategory extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // LẤY PARAM
        String cateParam = request.getParameter("categoryID");
        int cateID = 1; // mặc định

        if (cateParam != null && !cateParam.trim().isEmpty()) {
            try {
                cateID = Integer.parseInt(cateParam);
                System.out.println("categoryID = " + cateID);
            } catch (NumberFormatException e) {
                System.out.println("categoryID không hợp lệ: " + cateParam + " → dùng mặc định = 1");
            }
        }

        // LẤY DỮ LIỆU
        BookDAO bookDAO = BookDAO.getInstance();
        List<Book> list = bookDAO.getBookByCID(cateID);
        List<Category> listC = CategoryDAO.getInstance().getAllCategories();
        Book news = bookDAO.getNew();

        // GỬI QUA JSP
        request.setAttribute("listB", list);
        request.setAttribute("listC", listC);
        request.setAttribute("n", news);
        request.setAttribute("tag", cateParam); // để highlight menu

        // FORWARD
        request.getRequestDispatcher("views/home.jsp").forward(request, response);
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
