package Bai1;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MedicineDAO {
    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            //Tắt auto-commit
            conn.setAutoCommit(false);
            //Trừ thuốc
            String sql1 = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, medicineId);
            ps1.executeUpdate();

            //Test lỗi (bỏ comment để test rollback)
            // int x = 10/0;

            //Ghi lịch sử
            String sql2 = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, NOW())";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();
            //Commit nếu OK
            conn.commit();
            System.out.println("Cấp phát thuốc thành công!");
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback(); // rollback nếu lỗi
                    System.out.println("Rollback do lỗi!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
