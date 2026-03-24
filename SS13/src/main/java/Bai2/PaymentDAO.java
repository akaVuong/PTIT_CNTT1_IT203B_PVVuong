package Bai2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDAO {
    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            //Tắt auto-commit
            conn.setAutoCommit(false);
            // Trừ tiền
            String sql1 = "UPDATE Patient_wallet SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.executeUpdate();
            //Cập nhật hóa đơn (giả sử có lỗi)
            String sql2 = "UPDATE Invoicess SET status = 'PAID' WHERE invoice_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, invoiceId);
            ps2.executeUpdate();
            // Thành công → commit
            conn.commit();
            System.out.println("Thanh toán hoàn tất!");
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Đã rollback do lỗi!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Lỗi hệ thống: " + e.getMessage());

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
