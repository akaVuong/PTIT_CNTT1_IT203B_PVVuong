package dao;

import model.User;
import util.DBContext;
import java.sql.*;
import java.util.*;

public class UserDAO {

    // --- MỚI: Kiểm tra trùng Username ---
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm đăng ký (Cập nhật để trả về kết quả rõ ràng)
    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, full_name, role, department_id, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // Password đã được hash ở Menu
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getDepartmentId());
            ps.setString(6, user.getPhone());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("full_name"), rs.getString("role"),
                        rs.getInt("department_id"), rs.getString("phone")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<User> getAllUsersByRole(String role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("username"), "",
                        rs.getString("full_name"), rs.getString("role"),
                        rs.getInt("department_id"), rs.getString("phone")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}