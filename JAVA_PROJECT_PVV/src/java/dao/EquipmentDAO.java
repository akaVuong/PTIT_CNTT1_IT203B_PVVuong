package dao;

import model.Equipment;
import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    // 1. Hiển thị danh sách thiết bị
    public List<Equipment> getAll() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipments";
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("total_qty"),
                        rs.getInt("available_qty"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Kiểm tra trùng tên thiết bị (Để dùng khi THÊM)
    public boolean isNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM equipments WHERE name = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Thêm thiết bị mới
    public boolean addEquipment(Equipment e) {
        String sql = "INSERT INTO equipments (name, total_qty, available_qty, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setInt(2, e.getTotalQty());
            ps.setInt(3, e.getAvailableQty());
            ps.setString(4, e.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    // 4. Sửa/Cập nhật thiết bị (Đổi từ updateQty thành update để sửa được cả TÊN)
    public boolean updateEquipment(Equipment e) {
        String sql = "UPDATE equipments SET name = ?, total_qty = ?, available_qty = ?, status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setInt(2, e.getTotalQty());
            ps.setInt(3, e.getAvailableQty());
            ps.setString(4, e.getStatus());
            ps.setInt(5, e.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    // 5. Xóa thiết bị (Kiểm tra ràng buộc tự động qua lỗi SQLException nếu có khóa ngoại)
    public boolean deleteEquipment(int id) {
        String sql = "DELETE FROM equipments WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Nếu lỗi do ràng buộc khóa ngoại (đang được dùng trong đơn đặt phòng), e sẽ báo lỗi
            return false;
        }
    }

    // 6. Tìm thiết bị theo ID (Dùng để hiển thị thông tin cũ khi SỬA)
    public Equipment getById(int id) {
        String sql = "SELECT * FROM equipments WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Equipment(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getInt("total_qty"), rs.getInt("available_qty"), rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}