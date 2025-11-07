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
                sr.setUnitPrice(rs.getInt("UnitPrice"));
                sr.setTotalAmount(rs.getInt("TotalAmount"));
                sr.setOrderDate(rs.getDate("OrderDate"));
                sr.setStatus(rs.getString("Status"));

                list.add(sr);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;

    }

    public List<SalesReport> getReports(String search, String sort) {
        List<SalesReport> list = new ArrayList<>();

        // Câu lệnh SQL gốc
        StringBuilder sql = new StringBuilder("""
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
        WHERE o.Status = 'Completed'
    """);

        // Nếu có từ khóa tìm kiếm, thêm điều kiện WHERE với LIKE (tìm username hoặc book title)
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (u.UserName LIKE ? OR b.Title LIKE ?) ");
        }

        // Xử lý sort
        if (sort != null) {
            switch (sort) {
                case "username_asc":
                    sql.append(" ORDER BY u.UserName ASC");
                    break;
                case "username_desc":
                    sql.append(" ORDER BY u.UserName DESC");
                    break;
                case "book_asc":
                    sql.append(" ORDER BY b.Title ASC");
                    break;
                case "book_desc":
                    sql.append(" ORDER BY b.Title DESC");
                    break;
                case "date_asc":
                    sql.append(" ORDER BY o.OrderDate ASC");
                    break;
                case "date_desc":
                    sql.append(" ORDER BY o.OrderDate DESC");
                    break;
                case "quantity_asc":
                    sql.append(" ORDER BY od.Quantity ASC");
                    break;
                case "quantity_desc":
                    sql.append(" ORDER BY od.Quantity DESC");
                    break;
                case "amount_asc":
                    sql.append(" ORDER BY o.TotalAmount ASC");
                    break;
                case "amount_desc":
                    sql.append(" ORDER BY o.TotalAmount DESC");
                    break;
                default:
                    // Mặc định không sắp xếp
                    break;
            }
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql.toString());

            // Nếu có search thì set tham số LIKE
            if (search != null && !search.trim().isEmpty()) {
                String likeSearch = "%" + search.trim() + "%";
                ps.setString(1, likeSearch);
                ps.setString(2, likeSearch);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SalesReport sr = new SalesReport();
                sr.setUserName(rs.getString("UserName"));
                sr.setFullName(rs.getString("FullName"));
                sr.setBookTitle(rs.getString("BookTitle"));
                sr.setQuantity(rs.getInt("Quantity"));
                sr.setUnitPrice(rs.getInt("UnitPrice"));
                sr.setTotalAmount(rs.getInt("TotalAmount"));
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
