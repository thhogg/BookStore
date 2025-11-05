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
    
        public static void main(String[] args) {
        BookDAO dao = BookDAO.getInstance();
        List<Book> list = dao.getBookByCID("1");
        for (Book b : list) {
            System.out.println(b.getBookID() + " - " + b.getTitle());
        }
    }

}
