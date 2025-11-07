/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.SaleReportDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.SalesReport;

/**
 *
 * @author Leo
 */
public class ManageSaleReport extends HttpServlet {

    private SaleReportDAO salesReportDao;

    @Override
    public void init() {
        salesReportDao = SaleReportDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        try {
            
            List<SalesReport> salesReport = salesReportDao.getReports(search, sort);
            request.setAttribute("salesReport", salesReport);

            request.setAttribute("search", search);
            request.setAttribute("sort", sort);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error generating sales report.");
        }

        request.getRequestDispatcher("sales-report.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
