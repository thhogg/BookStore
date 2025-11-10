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
import model.DetailedSalesReport;
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
    // HÀM MỚI: DÙNG CHO TRANG SALES REPORTS
    // =======================================================
    /**
     * Lấy TẤT CẢ các đơn hàng đã 'Completed' (Hoàn thành).
     * Sắp xếp theo ngày mới nhất lên đầu (hợp lý cho báo cáo).
     */
    public List<Order> getCompletedOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM [Order] "
                   + "WHERE [Status] = 'Completed' "
                   + "ORDER BY OrderDate DESC";
                   
        try (PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            
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
        public List<DetailedSalesReport> getDetailedSalesReport(String searchKey, String sortBy) {
        List<DetailedSalesReport> list = new ArrayList<>();
        
        // 1. Câu SQL JOIN 4 bảng
        String sql = "SELECT "
                + "o.OrderID, o.OrderDate, o.TotalAmount AS OrderTotalAmount, o.[Status], o.UserName, "
                + "u.FullName AS CustomerFullName, "
                + "b.Title AS BookTitle, "
                + "od.Quantity, od.UnitPrice "
                + "FROM OrderDetail od "
                + "JOIN [Order] o ON od.OrderID = o.OrderID "
                + "JOIN Book b ON od.BookID = b.BookID "
                + "JOIN Users u ON o.UserName = u.UserName "
                + "WHERE o.[Status] = 'Completed' "; // Chỉ lấy đơn đã hoàn thành

        List<Object> params = new ArrayList<>();

        // 2. Thêm logic TÌM KIẾM
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql += "AND (o.UserName LIKE ? OR u.FullName LIKE ? OR b.Title LIKE ?) ";
            String searchPattern = "%" + searchKey.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
        }

        // 3. Thêm logic SẮP XẾP (an toàn, dùng whitelist)
        String orderBySql = "ORDER BY o.OrderDate ASC, o.OrderID ASC, b.Title ASC"; // Sắp xếp mặc định
        
        if ("date_desc".equals(sortBy)) {
            orderBySql = "ORDER BY o.OrderDate DESC, o.OrderID DESC, b.Title ASC";
        } else if ("customer_asc".equals(sortBy)) {
            orderBySql = "ORDER BY u.FullName ASC, o.OrderDate ASC, o.OrderID ASC";
        } else if ("book_asc".equals(sortBy)) {
            orderBySql = "ORDER BY b.Title ASC, o.OrderDate ASC, o.OrderID ASC";
        }
        
        sql += orderBySql;

        // 4. Thực thi truy vấn
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Gán các tham số (nếu có)
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DetailedSalesReport(
                            rs.getInt("OrderID"),
                            rs.getTimestamp("OrderDate"),
                            rs.getInt("OrderTotalAmount"),
                            rs.getString("Status"),
                            rs.getString("UserName"),
                            rs.getString("CustomerFullName"),
                            rs.getString("BookTitle"),
                            rs.getInt("Quantity"),
                            rs.getInt("UnitPrice")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
        /**
     * Lấy TẤT CẢ đơn hàng cho trang Admin, hỗ trợ Search và Sort
     * (Hàm này thay thế cho hàm getAllOrders() cũ)
     * @param searchKey Từ khóa tìm kiếm (có thể là null)
     * @param sortBy Tiêu chí sắp xếp (có thể là null)
     * @return Danh sách đơn hàng
     */
    public List<Order> getAdminOrders(String searchKey, String sortBy) {
        List<Order> list = new ArrayList<>();
        
        // 1. Câu SQL cơ bản (JOIN với Users để tìm kiếm theo tên)
        String sql = "SELECT o.* FROM [Order] o "
                   + "LEFT JOIN Users u ON o.UserName = u.UserName "
                   + "WHERE 1=1 "; // 1=1 là mẹo để dễ nối các AND

        List<Object> params = new ArrayList<>();

        // 2. Thêm logic TÌM KIẾM
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            // Tìm theo UserName, FullName (từ bảng Users), Status, hoặc OrderID
            sql += "AND (o.UserName LIKE ? OR u.FullName LIKE ? OR o.[Status] LIKE ? OR o.OrderID = ?) ";
            
            String searchPattern = "%" + searchKey.trim() + "%";
            params.add(searchPattern); // Tìm UserName
            params.add(searchPattern); // Tìm FullName
            params.add(searchPattern); // Tìm Status
            
            // Cố gắng tìm chính xác OrderID
            int orderIdKey = -1; // Mặc định là -1 nếu không phải số
            try {
                orderIdKey = Integer.parseInt(searchKey.trim());
            } catch (NumberFormatException e) { 
                // Bỏ qua nếu searchKey không phải là số
            }
            params.add(orderIdKey);
        }

        // 3. THÊM LOGIC SẮP XẾP
        String orderBySql;
        // Dùng switch-case để xử lý các lựa chọn
        switch (sortBy != null ? sortBy : "id_desc") { // Mặc định là id_desc
            case "id_asc":
                orderBySql = "ORDER BY o.OrderID ASC";
                break;
            case "date_desc":
                orderBySql = "ORDER BY o.OrderDate DESC";
                break;
            case "date_asc":
                orderBySql = "ORDER BY o.OrderDate ASC";
                break;
            case "total_desc":
                orderBySql = "ORDER BY o.TotalAmount DESC";
                break;
            case "total_asc":
                orderBySql = "ORDER BY o.TotalAmount ASC";
                break;
            case "id_desc":
            default:
                // Mặc định nếu sortBy là null hoặc không khớp
                orderBySql = "ORDER BY o.OrderID DESC"; 
                break;
        }
        
        sql += orderBySql; // Nối logic sắp xếp vào câu SQL chính

        // 4. Thực thi truy vấn
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Gán các tham số (cho phần TÌM KIẾM)
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Dùng constructor đầy đủ của Order
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
        public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM [Order] WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            
            int affectedRows = ps.executeUpdate();
            
            return affectedRows > 0; // Trả về true nếu có 1 hàng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}