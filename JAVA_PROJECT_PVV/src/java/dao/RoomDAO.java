package dao;

import model.Room;
import util.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // Sửa lại dòng 12 trong RoomDAO.java
    public List<model.Room> getAllRooms() {
        List<model.Room> list = new ArrayList<>();
        // Sử dụng CURRENT_TIMESTAMP để chính xác hơn NOW() trong một số cấu hình DB
        String sql = "SELECT r.*, " +
                "CASE WHEN b.id IS NOT NULL THEN 'DANG HOP' ELSE 'TRONG' END AS current_status " +
                "FROM rooms r " +
                "LEFT JOIN bookings b ON r.id = b.room_id " +
                "AND b.status = 'APPROVED' " +
                "AND CURRENT_TIMESTAMP BETWEEN b.start_time AND b.end_time";

        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new model.Room(
                        rs.getInt("id"),
                        rs.getString("room_name"),
                        rs.getInt("capacity"),
                        rs.getString("location"),
                        rs.getString("current_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_name, capacity, location, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getCapacity());
            ps.setString(3, room.getLocation());
            ps.setString(4, room.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET room_name = ?, capacity = ?, location = ?, status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getCapacity());
            ps.setString(3, room.getLocation());
            ps.setString(4, room.getStatus());
            ps.setInt(5, room.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM rooms WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}