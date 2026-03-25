package dao;

import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;
    public UserDAO(Connection conn) {
        this.conn = conn;
    }
    // lấy danh sách user từ DB
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try {
            // tạo câu lệnh SQL
            PreparedStatement ps = conn.prepareStatement(sql);

            // chạy query trả về dữ liệu dạng bảng
            ResultSet rs = ps.executeQuery();
            // duyệt từng dòng trong DB
            while (rs.next()) {
                // lấy dữ liệu từng cột map sang object
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("address")
                );
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách user");
            e.printStackTrace();
        }
        return list;
    }

    // thêm user vào DB
    public boolean insert(User user) {
        String sql = "INSERT INTO Users(full_name, address) VALUES (?, ?)";
        try {
            // tạo statement có tham số ?
            PreparedStatement ps = conn.prepareStatement(sql);
            // gán dữ liệu vào ?
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getAddress());
            // chạy lệnh insert
            int rows = ps.executeUpdate();
            // nếu có dòng bị ảnh hưởng thì thành công
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm user");
            e.printStackTrace();
        }
        return false;
    }
}