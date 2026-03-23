package Bai2;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VitalDAO {
    public void updateVital(int patientId, double temp, int heartRate) {
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán đúng kiểu dữ liệu
            ps.setDouble(1, temp);      // nhiệt độ
            ps.setInt(2, heartRate);    // nhịp tim
            ps.setInt(3, patientId);    // id bệnh nhân

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
