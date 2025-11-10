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
                     SELECT [UserName]
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

    public User getUserByUserName(String userName) {
        String sql = """
                     SELECT [UserName]
                           ,[Password]
                           ,[FullName]
                           ,[Email]
                           ,[Phone]
                           ,[Address]
                           ,[Role]
                           ,[CreatedAt]
                       FROM [dbo].[Users]
                     WHERE UserName = ? """;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
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

    public int insertUser(String userName, String pass, String fullname, String email, String phone, String address, String role) throws SQLException {
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
        
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, pass);
            ps.setString(3, fullname);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, role);
            
            return ps.executeUpdate();
        }
    }

    public int updateUser(String userName, User u) throws SQLException{
        String sql = """
                     UPDATE [dbo].[Users]
                        SET [UserName] = ?
                           ,[Password] = ?
                           ,[FullName] = ?
                           ,[Email] = ?
                           ,[Phone] = ?
                           ,[Address] = ?
                           ,[Role] = ?
                     WHERE UserName = ?""";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getRole());
            ps.setString(8, userName);
            
            return ps.executeUpdate();
        } 
    }

    public int deleteByUserName(String userName) throws SQLException {
        String sql = """
                     DELETE FROM [dbo].[Users]
                           WHERE UserName = ?""";
        
        try(PreparedStatement ps = connection.prepareStatement(sql)) {   
            ps.setString(1, userName);
            return ps.executeUpdate();
        } 
    }

    public int countAllUsers() {

        // SỬA SQL: Đếm UserName và thêm WHERE Role = 'Customer'
        String sql = "SELECT COUNT(UserName) AS Total "
                + "FROM [dbo].[Users] "
                + "WHERE [Role] = 'Customer'"; // <-- THÊM DÒNG NÀY

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

    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM Users WHERE UserName = ? AND Password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("UserName"),
                            rs.getString("Password"),
                            rs.getString("FullName"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("Role"),
                            rs.getDate("createdAt"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUsernameExist(String username) {
        String sql = "SELECT 1 FROM Users WHERE UserName = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(String username, String password, String fullName,
            String email, String phone, String address) {
        String sql = "INSERT INTO Users (UserName, Password, FullName, Email, Phone, Address, Role) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'Customer')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullName);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, address);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String userName, String oldPasword, String newPassword) {
        String query = "select password from users where userName = ?";
        String updateQuery = "update users set password = ? where userName =?";
        
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String currentPass = rs.getString("password");
                // kiem tra mk cu
                if(!currentPass.equals(oldPasword)){
                    return false;
                }
                
                //cap nhap mk moi
                try (PreparedStatement updatePs = connection.prepareStatement(updateQuery)){
                    updatePs.setString(1, newPassword);
                    updatePs.setString(2, userName);
                    return updatePs.executeUpdate() > 0;
                    
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
