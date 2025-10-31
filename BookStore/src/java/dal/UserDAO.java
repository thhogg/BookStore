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
}
