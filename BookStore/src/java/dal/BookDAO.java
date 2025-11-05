/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
/**
 *
 * @author Leo
 */
public class BookDAO extends DBContext {

    private static BookDAO instance;

    private BookDAO() {
    }

    public static synchronized BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = """
                     SELECT [BookID]
                           ,[Title]
                           ,[Author]
                           ,[Publisher]
                           ,[CategoryID]
                           ,[ISBN]
                           ,[Price]
                           ,[Stock]
                           ,[Description]
                           ,[ImageUrl]
                           ,[CreatedAt]
                     FROM [dbo].[Book]""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setBookID(rs.getInt("BookID"));
                b.setTitle(rs.getString("Title"));
                b.setAuthor(rs.getString("Author"));
                b.setPublisher(rs.getString("Publisher"));
                b.setCategoryID(rs.getInt("CategoryID"));
                b.setIsbn(rs.getString("ISBN"));
                b.setPrice(rs.getInt("Price"));
                b.setStock(rs.getInt("Stock"));
                b.setDescription(rs.getString("Description"));
                b.setImageUrl(rs.getString("ImageUrl"));
                b.setCreatedAt(rs.getDate("CreatedAt"));

                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
//    public List<Book> getAllBooks() {
//        List<Book> list = new ArrayList<>();
//        
//        // BƯỚC 1 SỬA LỖI: Sửa lại câu lệnh SQL
//        // Chúng ta JOIN (nối) với bảng Author và Publisher để lấy TÊN
//        // (Giả sử bảng của bạn là [dbo].[Author] có cột AuthorName
//        // và [dbo].[Publisher] có cột PublisherName)
//        String sql = """
//                     SELECT 
//                         b.[BookID], b.[Title],
//                         a.[AuthorName],  -- Lấy AuthorName từ bảng Author (giả sử tên là AuthorName)
//                         p.[PublisherName], -- Lấy PublisherName từ bảng Publisher (giả sử tên là PublisherName)
//                         b.[CategoryID], b.[ISBN], b.[Price], b.[Stock],
//                         b.[Description], b.[ImageUrl], b.[CreatedAt]
//                     FROM [dbo].[Book] AS b
//                     LEFT JOIN [dbo].[Author] AS a ON b.AuthorID = a.AuthorID
//                     LEFT JOIN [dbo].[Publisher] AS p ON b.PublisherID = p.PublisherID
//                     """;
//
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            // Logic "miếng băng" để mở lại connection nếu bị trang Admin đóng
//            if (connection == null || connection.isClosed()) {
//                System.out.println("DEBUG (getAllBooks): Connection đã bị đóng. Đang mở lại...");
//                String url = "jdbc:sqlserver://localhost:1433;databaseName=BookStoreDB;encrypt=false;trustServerCertificate=true;";
//                String username = "sa";
//                String password = "24012002";
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                connection = DriverManager.getConnection(url, username, password);
//                System.out.println("DEBUG (getAllBooks): Đã mở lại connection.");
//            }
//
//            // Dòng này sẽ là dòng 93 (gây ra lỗi)
//            ps = connection.prepareStatement(sql); 
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Book b = new Book();
//                b.setBookID(rs.getInt("BookID"));
//                b.setTitle(rs.getString("Title"));
//                
//                // BƯỚC 2 SỬA LỖI: Sửa lại tên cột Java đọc
//                // Giờ chúng ta đọc cột "AuthorName" và "PublisherName" từ SQL
//                b.setAuthor(rs.getString("AuthorName"));
//                b.setPublisher(rs.getString("PublisherName"));
//                
//                b.setCategoryID(rs.getInt("CategoryID"));
//                b.setIsbn(rs.getString("ISBN"));
//                b.setPrice(rs.getInt("Price"));
//                b.setStock(rs.getInt("Stock"));
//                b.setDescription(rs.getString("Description"));
//                b.setImageUrl(rs.getString("ImageUrl"));
//                b.setCreatedAt(rs.getDate("CreatedAt"));
//
//                list.add(b);
//            }
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace(); // In lỗi ra nếu có
//        } finally {
//            // Luôn luôn đóng ps và rs sau khi dùng
//            try {
//                if (rs != null) rs.close();
//                if (ps != null) ps.close();
//            } catch (SQLException e2) {
//                e2.printStackTrace();
//            }
//        }
//        
//        return list;
//    }

    public Book getBookByBookID(int bookID) {
        String sql = """
                     SELECT [BookID]
                           ,[Title]
                           ,[Author]
                           ,[Publisher]
                           ,[CategoryID]
                           ,[ISBN]
                           ,[Price]
                           ,[Stock]
                           ,[Description]
                           ,[ImageUrl]
                           ,[CreatedAt]
                     FROM [dbo].[Book]
                     WHERE BookID = ? """;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book b = new Book();
                b.setBookID(rs.getInt("BookID"));
                b.setTitle(rs.getString("Title"));
                b.setAuthor(rs.getString("Author"));
                b.setPublisher(rs.getString("Publisher"));
                b.setCategoryID(rs.getInt("CategoryID"));
                b.setIsbn(rs.getString("ISBN"));
                b.setPrice(rs.getInt("Price"));
                b.setStock(rs.getInt("Stock"));
                b.setDescription(rs.getString("Description"));
                b.setImageUrl(rs.getString("ImageUrl"));
                b.setCreatedAt(rs.getDate("CreatedAt"));

                return b;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
//    public Book getBookByBookID(int bookID) {
//        
//        // SỬA LỖI SQL: Dùng JOIN để lấy TÊN Tác giả và TÊN NXB
//        String sql = """
//                     SELECT 
//                         b.[BookID], b.[Title],
//                         a.[AuthorName],  -- Lấy AuthorName từ bảng Author
//                         p.[PublisherName], -- Lấy PublisherName từ bảng Publisher
//                         b.[CategoryID], b.[ISBN], b.[Price], b.[Stock],
//                         b.[Description], b.[ImageUrl], b.[CreatedAt]
//                     FROM [dbo].[Book] AS b
//                     LEFT JOIN [dbo].[Author] AS a ON b.AuthorID = a.AuthorID
//                     LEFT JOIN [dbo].[Publisher] AS p ON b.PublisherID = p.PublisherID
//                     WHERE b.[BookID] = ? 
//                     """;
//
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            // Logic "miếng băng" để mở lại connection nếu bị trang Admin đóng
//            if (connection == null || connection.isClosed()) {
//                System.out.println("DEBUG (getBookByID): Connection đã bị đóng. Đang mở lại...");
//                String url = "jdbc:sqlserver://localhost:1433;databaseName=BookStoreDB;encrypt=false;trustServerCertificate=true;";
//                String username = "sa";
//                String password = "24012002";
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                connection = DriverManager.getConnection(url, username, password);
//                System.out.println("DEBUG (getBookByID): Đã mở lại connection.");
//            }
//
//            ps = connection.prepareStatement(sql);
//            ps.setInt(1, bookID); // Set bookID cho mệnh đề WHERE
//            rs = ps.executeQuery();
//
//            if (rs.next()) { // Chỉ 'if' vì chỉ có 1 kết quả
//                Book b = new Book();
//                b.setBookID(rs.getInt("BookID"));
//                b.setTitle(rs.getString("Title"));
//                
//                // SỬA LỖI Java: Lấy đúng tên cột "AuthorName", "PublisherName"
//                b.setAuthor(rs.getString("AuthorName"));
//                b.setPublisher(rs.getString("PublisherName"));
//                
//                b.setCategoryID(rs.getInt("CategoryID"));
//                b.setIsbn(rs.getString("ISBN"));
//                b.setPrice(rs.getInt("Price"));
//                b.setStock(rs.getInt("Stock"));
//                b.setDescription(rs.getString("Description"));
//                b.setImageUrl(rs.getString("ImageUrl"));
//                b.setCreatedAt(rs.getDate("CreatedAt"));
//
//                return b; // Trả về cuốn sách tìm thấy
//            }
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace(); // In lỗi ra nếu có
//        } finally {
//            // Luôn luôn đóng ps và rs sau khi dùng
//            try {
//                if (rs != null) rs.close();
//                if (ps != null) ps.close();
//            } catch (SQLException e2) {
//                e2.printStackTrace();
//            }
//        }
//        
//        return null; // Trả về null nếu không tìm thấy hoặc có lỗi
//    }

    public void insertBook(Book b) {
        String sql = """
                     INSERT INTO [dbo].[Book]
                                ([Title]
                                ,[Author]
                                ,[Publisher]
                                ,[CategoryID]
                                ,[ISBN]
                                ,[Price]
                                ,[Stock]
                                ,[Description]
                                ,[ImageUrl]
                                ,[CreatedAt])
                        VALUES
                                (?,?,?,?,?,?,?,?,?,?)""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getPublisher());
            ps.setInt(4, b.getCategoryID());
            ps.setString(5, b.getIsbn());
            ps.setInt(6, b.getPrice());
            ps.setInt(7, b.getStock());
            ps.setString(8, b.getDescription());
            ps.setString(9, b.getImageUrl());
            ps.setDate(10, b.getCreatedAt());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateBook(int bookId, Book b) {
        String sql = """
                     UPDATE [dbo].[Book]
                     SET [Title] = ?
                           ,[Author] = ?
                           ,[Publisher] = ?
                           ,[CategoryID] = ?
                           ,[ISBN] = ?
                           ,[Price] = ?
                           ,[Stock] = ?
                           ,[Description] = ?
                           ,[ImageUrl] = ?
                     WHERE BookID = ?""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getPublisher());
            ps.setInt(4, b.getCategoryID());
            ps.setString(5, b.getIsbn());
            ps.setInt(6, b.getPrice());
            ps.setInt(7, b.getStock());
            ps.setString(8, b.getDescription());
            ps.setString(9, b.getImageUrl());
            ps.setInt(10, bookId);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteById(int id) {
        String sql = """
                     DELETE FROM [dbo].[Book]
                           WHERE BookID = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    public int countAllBooks() {
        String sql = "SELECT COUNT(BookID) AS Total FROM [dbo].[Book]";
        int total = 0;
        
        // SỬ DỤNG TRY-WITH-RESOURCES để tự động đóng tài nguyên
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { 
            
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException e) {
            // Thay thế System.out.println(e) bằng Logging (nên dùng)
            // hoặc ném ngoại lệ RuntimeException để Controller xử lý
            e.printStackTrace(); 
        }
        return total;
    }
    public Book getNew() {
        String query = "select top 1 * from Book order by BookID desc";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Book(
                        rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getInt("CategoryID"),
                        rs.getString("ISBN"),
                        rs.getInt("Price"),
                        rs.getInt("Stock"),
                        rs.getString("Description"),
                        rs.getString("ImageUrl"));
                        
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    
    public List<Book> getBookByCID(String categoryID) {
        List<Book> list = new ArrayList<>();
        String query = "select * from Book \n"
                + "where CategoryID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, categoryID);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(new Book(rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getInt("CategoryID"),
                        rs.getString("ISBN"),
                        rs.getInt("Price"),
                        rs.getInt("Stock"),
                        rs.getString("Description"),
                        rs.getString("ImageUrl")));
            }
        } catch (Exception e) {
            
        }
        return list;
        
    }
}
