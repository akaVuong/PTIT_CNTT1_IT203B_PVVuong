package dao;

import model.Booking;
import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // 1. Kiểm tra trùng lịch
    public boolean isRoomAvailable(int roomId, java.time.LocalDateTime start, java.time.LocalDateTime end) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE room_id = ? " +
                "AND status IN ('PENDING', 'APPROVED') " +
                "AND (? < end_time AND ? > start_time)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(start));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(end));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) == 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    // 2. Tạo đơn mới (Đã thêm equipment_id)
    public boolean createBooking(Booking b) {
        String sql = "INSERT INTO bookings (user_id, room_id, equipment_id, start_time, end_time, purpose, status) VALUES (?, ?, ?, ?, ?, ?, 'PENDING')";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, b.getUserId());
            ps.setInt(2, b.getRoomId());
            ps.setInt(3, b.getEquipmentId()); // Lưu ID thiết bị nhân viên chọn
            ps.setTimestamp(4, Timestamp.valueOf(b.getStartTime()));
            ps.setTimestamp(5, Timestamp.valueOf(b.getEndTime()));
            ps.setString(6, b.getPurpose());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // 3. Lấy danh sách chờ duyệt cho Admin (Đã thêm equipment_id)
    public List<Booking> getPendingBookings() {
        List<Booking> list = new ArrayList<>();
        // Câu lệnh SQL mới: Lấy thêm tên thiết bị và số lượng sẵn có từ bảng equipments
        String sql = "SELECT b.*, e.name as equip_name, e.available_qty " +
                "FROM bookings b " +
                "LEFT JOIN equipments e ON b.equipment_id = e.id " +
                "WHERE b.status = 'PENDING'";

        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Booking b = new Booking(
                        rs.getInt("id"), rs.getInt("user_id"), rs.getInt("room_id"),
                        rs.getInt("equipment_id"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getString("purpose"), rs.getString("status")
                );
                // Tận dụng field 'purpose' hoặc tạo biến tạm để truyền thông tin hiển thị
                // Ở đây mình sẽ "mượn" tạm cách gán dữ liệu phụ vào object nếu model của bạn có chỗ chứa
                // Nếu không, chỉ cần đảm bảo SQL này chạy đúng để AdminMenu gọi dữ liệu.
                list.add(b);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // 4. Duyệt & Giao việc
    public boolean approveAndAssign(int bId, int sId) {
        String sql = "UPDATE bookings SET status = 'APPROVED', assigned_support_id = ?, preparation_status = 'PREPARING' WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sId);
            ps.setInt(2, bId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // 5. Support cập nhật READY -> Tự động trừ đúng thiết bị trong đơn
    public boolean updatePrepStatus(int bId, String status) {
        String sqlUpdateBooking = "UPDATE bookings SET preparation_status = ? WHERE id = ?";
        // Trừ kho đúng thiết bị gắn với đơn đặt phòng này
        String sqlUpdateStock = "UPDATE equipments e " +
                "JOIN bookings b ON e.id = b.equipment_id " +
                "SET e.available_qty = e.available_qty - 1 " +
                "WHERE b.id = ? AND e.available_qty > 0";

        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);

            // Cập nhật trạng thái chuẩn bị
            PreparedStatement ps1 = conn.prepareStatement(sqlUpdateBooking);
            ps1.setString(1, status);
            ps1.setInt(2, bId);
            ps1.executeUpdate();

            // Nếu READY và đơn đó CÓ mượn thiết bị (equipId > 0)
            if (status.equals("READY")) {
                PreparedStatement ps2 = conn.prepareStatement(sqlUpdateStock);
                ps2.setInt(1, bId);
                ps2.executeUpdate();
                // Không check rows == 0 vì có thể đơn này không mượn thiết bị (equipId = 0)
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            return false;
        }
    }

    // 6. Xem lịch họp của tôi
    public void showMyBookings(int userId) {
        String sql = "SELECT b.id, r.room_name, r.location, b.start_time, b.end_time, b.status, b.preparation_status " +
                "FROM bookings b JOIN rooms r ON b.room_id = r.id " +
                "WHERE b.user_id = ? ORDER BY b.start_time DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========================================== LỊCH HỌP CỦA TÔI ==========================================");
            System.out.printf("%-3s | %-15s | %-10s | %-16s | %-16s | %-10s | %-10s\n",
                    "ID", "Phòng", "Vị trí", "Bắt đầu", "Kết thúc", "Duyệt", "Chuẩn bị");
            System.out.println("------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-3d | %-15s | %-10s | %-16s | %-16s | %-10s | %-10s\n",
                        rs.getInt("id"), rs.getString("room_name"), rs.getString("location"),
                        rs.getTimestamp("start_time").toString().substring(0, 16),
                        rs.getTimestamp("end_time").toString().substring(0, 16),
                        rs.getString("status"), rs.getString("preparation_status"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 7. Support xem việc (Đã thêm tên thiết bị yêu cầu)
    public void showAssignedTasks(int sId) {
        String sql = "SELECT b.id, r.room_name, r.location, e.name as equip_name, b.start_time, b.end_time, b.purpose, b.preparation_status " +
                "FROM bookings b " +
                "JOIN rooms r ON b.room_id = r.id " +
                "LEFT JOIN equipments e ON b.equipment_id = e.id " +
                "WHERE b.assigned_support_id = ? AND b.status = 'APPROVED' " +
                "ORDER BY b.start_time ASC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========================================== DANH SÁCH CÔNG VIỆC ĐƯỢC GIAO ==========================================");
            System.out.printf("%-3s | %-12s | %-10s | %-15s | %-16s | %-16s | %-10s\n",
                    "ID", "Phòng", "Vị trí", "Thiết bị mượn", "Bắt đầu", "Kết thúc", "Trạng thái");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String equipName = rs.getString("equip_name") == null ? "Không mượn" : rs.getString("equip_name");
                System.out.printf("%-3d | %-12s | %-10s | %-15s | %-16s | %-16s | [%s]\n",
                        rs.getInt("id"), rs.getString("room_name"), rs.getString("location"),
                        equipName,
                        rs.getTimestamp("start_time").toString().substring(0, 16),
                        rs.getTimestamp("end_time").toString().substring(0, 16),
                        rs.getString("preparation_status"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}