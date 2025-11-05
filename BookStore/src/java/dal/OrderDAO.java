    package dal;

    import java.util.ArrayList;
    import java.util.List;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.LinkedHashMap;
    import java.util.Map;
    import model.Order;
    import model.OrderDetail;

    public class OrderDAO extends DBContext {

        private static OrderDAO instance;

        // Cấu trúc Singleton, giống hệt BookDAO
        private OrderDAO() {
        }

        public static synchronized OrderDAO getInstance() {
            if (instance == null) {
                instance = new OrderDAO();
            }
            return instance;
        }

        /**
         * Đếm tổng số đơn hàng đã đặt. Giả sử bảng của bạn tên là [Order] và cột
         * khóa chính là [OrderID]
         */
        public int countAllOrders() {
            String sql = "SELECT COUNT(OrderID) AS Total FROM [dbo].[Order]";
            int total = 0;

            // Dùng try-with-resources để tự động đóng ps và rs
            // connection là biến được kế thừa từ DBContext
            try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    total = rs.getInt("Total");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // In lỗi ra console
            }
            return total;
        }

        /**
         * Tính tổng doanh thu từ tất cả các đơn hàng. Giả sử bảng của bạn tên là
         * [Order] và cột tiền là [TotalPrice]
         */
        public double getTotalRevenue() {
            // Bạn có thể muốn thêm điều kiện (ví dụ: WHERE Status = 'Completed')
            String sql = "SELECT SUM(TotalAmount) AS Revenue FROM [dbo].[Order]";
            double revenue = 0;

            try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // Dùng getDouble để lấy tổng tiền
                    revenue = rs.getDouble("Revenue");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // In lỗi ra console
            }
            return revenue;
        }

        // (Sau này bạn sẽ thêm các hàm khác vào đây, ví dụ:
        // public void insertOrder(Order order) { ... }
        // public List<Order> getOrdersByUserID(int userID) { ... }
        // )
        public int addOrder(Order order) {
            // Lệnh INSERT ĐƠN GIẢN HÓA (Bỏ OUTPUT INSERTED.OrderID)
            String insertSql = "INSERT INTO [dbo].[Order] "
                    + "([UserID], [OrderDate], [TotalAmount], [PaymentMethod], [Status], [ShippingAddress]) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            // Lệnh SELECT để lấy ID tự tăng VỪA ĐƯỢC TẠO
            String selectIdSql = "SELECT SCOPE_IDENTITY() AS NewOrderID"; // Lệnh tiêu chuẩn của SQL Server

            int newOrderID = -1;

            try (PreparedStatement psInsert = connection.prepareStatement(insertSql); PreparedStatement psSelect = connection.prepareStatement(selectIdSql)) {
                // 1. CHẠY LỆNH INSERT
                psInsert.setInt(1, order.getUserID());
                psInsert.setDate(2, order.getOrderDate());
                psInsert.setInt(3, order.getTotalAmount());
                psInsert.setString(4, order.getPaymentMethod());
                psInsert.setString(5, order.getStatus());
                psInsert.setString(6, order.getShippingAddress());

                int affectedRows = psInsert.executeUpdate();

                if (affectedRows > 0) {
                    // 2. CHẠY LỆNH SELECT ID VỪA ĐƯỢC TẠO
                    try (ResultSet rs = psSelect.executeQuery()) {
                        if (rs.next()) {
                            newOrderID = rs.getInt("NewOrderID");
                            return newOrderID;
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
         *
         * @param detail Đối tượng OrderDetail
         */
        public void addOrderDetail(OrderDetail detail) {
            // Câu lệnh SQL dựa trên cấu trúc bảng của bạn
            String sql = "INSERT INTO [dbo].[OrderDetail] "
                    + "([OrderID], [BookID], [Quantity], [UnitPrice]) "
                    + "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, detail.getOrderID());
                ps.setInt(2, detail.getBookID());
                ps.setInt(3, detail.getQuantity());
                ps.setInt(4, detail.getUnitPrice());

                ps.executeUpdate(); // Thực thi insert

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<Order> getOrdersByUserID(int userID) {
            List<Order> list = new ArrayList<>();

            // Sắp xếp theo ngày mới nhất lên trước
            String sql = "SELECT * FROM [dbo].[Order] WHERE [UserID] = ? ORDER BY [OrderDate] DESC";

            // 'connection' là biến kế thừa từ DBContext
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, userID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Order order = new Order();
                        order.setOrderID(rs.getInt("OrderID"));
                        order.setUserID(rs.getInt("UserID"));
                        order.setOrderDate(rs.getDate("OrderDate"));
                        order.setTotalAmount(rs.getInt("TotalAmount"));
                        order.setPaymentMethod(rs.getString("PaymentMethod"));
                        order.setStatus(rs.getString("Status"));
                        order.setShippingAddress(rs.getString("ShippingAddress"));

                        list.add(order);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return list; // Trả về danh sách (có thể rỗng nếu user chưa mua hàng)
        }

        
}
    