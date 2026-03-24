package Bai5;

import java.sql.*;

public class ReceptionController {
    public void admitPatient(String name, int age, int bedId, double amount) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Check giường trống
            String check = "SELECT status FROM Bed WHERE bed_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(check);
            psCheck.setInt(1, bedId);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next() || !"EMPTY".equals(rs.getString("status"))) {
                throw new Exception("Giường không hợp lệ hoặc đã có người!");
            }

            // Insert bệnh nhân
            String sql1 = "INSERT INTO Patient(name, age, bed_id) VALUES (?, ?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, name);
            ps1.setInt(2, age);
            ps1.setInt(3, bedId);
            ps1.executeUpdate();

            ResultSet keys = ps1.getGeneratedKeys();
            keys.next();
            int patientId = keys.getInt(1);

            //Update giường
            String sql2 = "UPDATE Bed SET status = 'OCCUPIED' WHERE bed_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, bedId);
            if (ps2.executeUpdate() == 0) {
                throw new Exception("Cập nhật giường thất bại!");
            }

            //Insert tài chính
            String sql3 = "INSERT INTO Finance(patient_id, amount) VALUES (?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, patientId);
            ps3.setDouble(2, amount);
            ps3.executeUpdate();

            conn.commit();
            System.out.println("Tiếp nhận thành công!");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {}

            System.out.println("Lỗi: " + e.getMessage());

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {}
        }
    }
}
