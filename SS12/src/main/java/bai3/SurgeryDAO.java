package bai3;

import java.sql.*;

public class SurgeryDAO {
    public double getSurgeryFee(int surgeryId) {
        double cost = 0;
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall("{call GET_SURGERY_FEE(?, ?)}")) {
            // IN parameter
            cstmt.setInt(1, surgeryId);
            // OUT parameter (QUAN TRỌNG)
            cstmt.registerOutParameter(2, Types.DECIMAL);
            // Thực thi
            cstmt.execute();
            // Lấy kết quả
            cost = cstmt.getDouble(2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cost;
    }
}
