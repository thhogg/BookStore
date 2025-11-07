/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.SalesReport;

/**
 *
 * @author Leo
 */
public class SaleReportDAO extends DBContext {

    private static SaleReportDAO instance;

    private SaleReportDAO() {
    }

    public static synchronized SaleReportDAO getInstance() {
        if (instance == null) {
            instance = new SaleReportDAO();
        }
        return instance;
    }

    public List<SalesReport> getAllReports() {
        List<SalesReport> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         u.UserName,
                         u.FullName,
                         b.Title AS BookTitle,
                         od.Quantity,
                         od.UnitPrice,
                         o.TotalAmount,
                     	o.OrderDate,
                         o.Status
                     FROM Users u
                     JOIN [Order] o ON u.UserName = o.UserName
                     JOIN OrderDetail od ON o.OrderID = od.OrderID
                     JOIN Book b ON od.BookID = b.BookID
                     WHERE Status = 'Completed'""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SalesReport sr = new SalesReport();
                sr.setUserName(rs.getString("UserName"));
                sr.setFullName(rs.getString("FullName"));
                sr.setBookTitle(rs.getString("BookTitle"));
                sr.setQuantity(rs.getInt("Quantity"));
                sr.setUnitPrice(rs.getDouble("UnitPrice"));
                sr.setTotalAmount(rs.getDouble("TotalAmount"));
                sr.setOrderDate(rs.getDate("OrderDate"));
                sr.setStatus(rs.getString("Status"));

                list.add(sr);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;

    }
}
