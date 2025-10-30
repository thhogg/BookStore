/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.DBContext;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
