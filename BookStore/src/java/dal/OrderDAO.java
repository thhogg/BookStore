package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // <-- Đảm bảo import đúng
import java.util.ArrayList;
import java.util.List;
import model.BestSeller;
import model.CustomerRanking; // Import model mới
import model.Order;
import model.OrderDetail; // Import OrderDetail

public class OrderDAO extends DBContext {

    private static OrderDAO instance;

    // Cấu trúc Singleton
    private OrderDAO() {
    }

    public static synchronized OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    /**
     * Đếm tổng số đơn hàng đã đặt.
     */
    public int countAllOrders() {
        String sql = "SELECT COUNT(OrderID) AS Total FROM [dbo].[Order]";
        int total = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * Tính tổng doanh thu từ tất cả các đơn hàng (TotalAmount là INT).
     */
    public long getTotalRevenue() {

        String sql = "SELECT SUM(CAST(TotalAmount AS BIGINT)) AS Revenue "
                + "FROM [dbo].[Order] "
                + "WHERE [Status] = 'Completed'";

        long revenue = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                revenue = rs.getLong("Revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenue;
    }

    /**
     * Thêm một đơn hàng mới vào CSDL.
     */
    public int addOrder(Order order) {
        String sql = "INSERT INTO [dbo].[Order] "
                + "([UserName], [OrderDate], [TotalAmount], [PaymentMethod], [Status], [ShippingAddress]) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        int newOrderID = -1; 
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getUserName());
            ps.setTimestamp(2, order.getOrderDate());
            ps.setInt(3, order.getTotalAmount());
            ps.setString(4, order.getPaymentMethod());
            ps.setString(5, order.getStatus());
            ps.setString(6, order.getShippingAddress());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        newOrderID = rs.getInt(1); 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return newOrderID; 
    }

    /**
     * Thêm một chi tiết đơn hàng (một sản phẩm) vào DB.
     */
    public void addOrderDetail(OrderDetail detail) {
        String sql = "INSERT INTO [dbo].[OrderDetail] "
                + "([OrderID], [BookID], [Quantity], [UnitPrice]) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, detail.getOrderID());
            ps.setInt(2, detail.getBookID());
            ps.setInt(3, detail.getQuantity());
            ps.setInt(4, detail.getUnitPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy tất cả đơn hàng của một người dùng dựa trên UserName.
     */
    public List<Order> getOrdersByUserName(String userName) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Order] WHERE [UserName] = ? ORDER BY [OrderDate] DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            rs.getString("UserName"),
                            rs.getTimestamp("OrderDate"),
                            rs.getInt("TotalAmount"),
                            rs.getString("PaymentMethod"),
                            rs.getString("Status"),
                            rs.getString("ShippingAddress")
                    );
                    list.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy TẤT CẢ đơn hàng để admin quản lý (sắp xếp theo ID giảm dần)
     */
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM [Order] ORDER BY CAST(OrderID AS INT) DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Order(
                        rs.getInt("OrderID"),
                        rs.getString("UserName"),
                        rs.getTimestamp("OrderDate"), 
                        rs.getInt("TotalAmount"),
                        rs.getString("PaymentMethod"),
                        rs.getString("Status"),
                        rs.getString("ShippingAddress")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; 
    }

    /**
     * Cập nhật trạng thái đơn hàng (Admin dùng)
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE [Order] SET [Status] = ? WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy trạng thái hiện tại của đơn hàng.
     */
    public String getOrderStatusById(int orderId) {
        String sql = "SELECT [Status] FROM [Order] WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // <-- KHỐI CODE BỊ TRÙNG LẶP ĐÃ BỊ XÓA Ở ĐÂY -->

    /**
     * Lấy tất cả chi tiết (sản phẩm) của một đơn hàng.
     */
    public List<OrderDetail> getOrderDetailsByOrderID(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetail WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Giả sử OrderDetail có constructor (orderDetailID, orderID, bookID, quantity, unitPrice)
                    list.add(new OrderDetail(
                            rs.getInt("OrderDetailID"),
                            rs.getInt("OrderID"),
                            rs.getInt("BookID"),
                            rs.getInt("Quantity"),
                            rs.getInt("UnitPrice")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =======================================================
    // HÀM MỚI CHO BƯỚC 2 (DASHBOARD)
    // =======================================================
    
    /**
     * Đếm tổng số sách đã bán (chỉ từ các đơn "Hoàn thành")
     */
    public int countTotalBooksSold() {
        String sql = "SELECT SUM(od.Quantity) AS Total "
                + "FROM OrderDetail od "
                + "JOIN [Order] o ON od.OrderID = o.OrderID "
                + "WHERE o.[Status] = 'Completed'";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Đếm số lượng khách hàng (duy nhất) đã mua hàng (đơn "Hoàn thành")
     */
    public int countPurchasingCustomers() {
        String sql = "SELECT COUNT(DISTINCT UserName) AS Total "
                + "FROM [Order] WHERE [Status] = 'Completed'";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy bảng xếp hạng khách hàng (mua nhiều nhất, dựa trên đơn "Hoàn thành")
     */
    public List<CustomerRanking> getCustomerPurchaseRanking() {
        List<CustomerRanking> list = new ArrayList<>();
        String sql = "SELECT UserName, "
                + "SUM(TotalAmount) AS TotalSpent, "
                + "COUNT(OrderID) AS OrderCount "
                + "FROM [Order] "
                + "WHERE [Status] = 'Completed' "
                + "GROUP BY UserName "
                + "ORDER BY TotalSpent DESC"; 

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new CustomerRanking(
                        rs.getString("UserName"),
                        rs.getLong("TotalSpent"),
                        rs.getInt("OrderCount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<BestSeller> getBestSellingBooks(int topN) {
        List<BestSeller> list = new ArrayList<>();
        
        String sql = "SELECT TOP (?) b.Title, SUM(od.Quantity) AS TotalSold "
                   + "FROM OrderDetail od "
                   + "JOIN [Order] o ON od.OrderID = o.OrderID "
                   + "JOIN Book b ON od.BookID = b.BookID "
                   + "WHERE o.[Status] = 'Completed' "
                   + "GROUP BY b.Title "
                   + "ORDER BY TotalSold DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, topN); // Đặt tham số TOP N
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new BestSeller(
                            rs.getString("Title"),
                            rs.getInt("TotalSold")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}