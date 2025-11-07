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
import model.Category;

/**
 *
 * @author Leo
 */
public class CategoryDAO extends DBContext {

    private static CategoryDAO instance;

    private CategoryDAO() {
    }

    public static synchronized CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = """
                     SELECT [CategoryID]
                           ,[CategoryName]
                           ,[Description]
                     FROM [dbo].[Category]""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getString("Description"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Category getCategoryById(int id) {
        String sql = """
                     SELECT [CategoryID]
                           ,[CategoryName]
                           ,[Description]
                     FROM [dbo].[Category]
                     WHERE CategoryID = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Category c = new Category(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getString("Description"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public void insertCategory(String categoryName, String description) {
        String sql = """
                     INSERT INTO [dbo].[Category]
                                ([CategoryName]
                                ,[Description])
                      VALUES
                      (?,?)""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, categoryName);
            ps.setString(2, description);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void updateCategory(int id, Category c) {
        String sql = """
                     UPDATE [dbo].[Category]
                        SET [CategoryName] = ?
                           ,[Description] = ?
                      WHERE CategoryID = ?""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteById(int id) {
        String sql = """
                     DELETE FROM [dbo].[Category]
                           WHERE CategoryID = ?""";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
