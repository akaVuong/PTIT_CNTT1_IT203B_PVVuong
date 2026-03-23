package Bai1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorDAO {

    public boolean login(String code, String pass) {
        String sql = "select * from Doctors where code = ? and pass = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Gán giá trị vào dấu ?
            ps.setString(1, code);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // có dữ liệu => login thành công
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
