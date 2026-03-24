package Bai3;

import java.sql.*;

public class HospitalDAO {

    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // bắt đầu transaction
            //BẪY 1: CHECK SỐ DƯ
            String sqlCheck = "SELECT balance FROM Patient_wallet WHERE patient_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next()) {
                throw new Exception("Bệnh nhân không tồn tại!");
            }

            double balance = rs.getDouble("balance");

            //Bẫy 1: Không đủ tiền → rollback
            if (balance < tienVienPhi) {
                throw new Exception("Không đủ tiền thanh toán!");
            }

            //TRỪ TIỀN
            String sql1 = "UPDATE Patient_wallet SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setDouble(1, tienVienPhi);
            ps1.setInt(2, maBenhNhan);
            int row1 = ps1.executeUpdate();

            // UPDATE GIƯỜNG
            String sql2 = "UPDATE Bed SET status = 'EMPTY' WHERE patient_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, maBenhNhan);
            int row2 = ps2.executeUpdate();

            //UPDATE BỆNH NHÂN
            String sql3 = "UPDATE Patient SET status = 'DISCHARGED' WHERE patient_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, maBenhNhan);
            int row3 = ps3.executeUpdate();

            // BẪY 2: ROW AFFECTED
            if (row1 == 0 || row2 == 0 || row3 == 0) {
                throw new Exception("Cập nhật thất bại (Row affected = 0)!");
            }

            // OK hết → commit
            conn.commit();
            System.out.println("Xuất viện thành công!");

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback(); // rollback khi lỗi
                    System.out.println("Rollback do lỗi!");
                }
            } catch (SQLException ex) {
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
