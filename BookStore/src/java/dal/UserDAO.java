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
import model.User;

/**
 *
 * @author Leo
 */
public class UserDAO extends DBContext {

    private static UserDAO instance;

    private UserDAO() {
    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = """
                     SELECT [UserID]
                           ,[UserName]
                           ,[Password]
                           ,[FullName]
                           ,[Email]
                           ,[Phone]
                           ,[Address]
                           ,[Role]
                           ,[CreatedAt]
                       FROM [dbo].[Users]""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUserName(rs.getString("UserName"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setAddress(rs.getString("Address"));
                u.setRole(rs.getString("Role"));
                u.setCreatedAt(rs.getDate("CreatedAt"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public User getUserById(int id) {
        String sql = """
                     SELECT [UserID]
                           ,[UserName]
                           ,[Password]
                           ,[FullName]
                           ,[Email]
                           ,[Phone]
                           ,[Address]
                           ,[Role]
                           ,[CreatedAt]
                       FROM [dbo].[Users]
                     WHERE UserID = ? """;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUserName(rs.getString("UserName"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setAddress(rs.getString("Address"));
                u.setRole(rs.getString("Role"));
                u.setCreatedAt(rs.getDate("CreatedAt"));

                return u;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void insertUser(String userName, String pass, String fullname, String email, String phone, String address, String role) {
        String sql = """
                     INSERT INTO [dbo].[Users]
                                ([UserName]
                                ,[Password]
                                ,[FullName]
                                ,[Email]
                                ,[Phone]
                                ,[Address]
                                ,[Role])
                          VALUES
                                (?,?,?,?,?,?,?)""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, pass);
            ps.setString(3, fullname);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, role);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateUser(int id, User u) {
        String sql = """
                     UPDATE [dbo].[Users]
                        SET [UserName] = ?
                           ,[Password] = ?
                           ,[FullName] = ?
                           ,[Email] = ?
                           ,[Phone] = ?
                           ,[Address] = ?
                           ,[Role] = ?
                     WHERE UserID = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getRole());
            ps.setInt(8, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteById(int id) {
        String sql = """
                     DELETE FROM [dbo].[Users]
                           WHERE UserID = ?""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
