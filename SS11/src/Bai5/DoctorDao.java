package Bai5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {
    public List<Doctor> getAllDoctors() throws SQLException, ClassNotFoundException {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";
        try (Connection conn = Config.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Doctor(rs.getString("id"), rs.getString("name"), rs.getString("specialty")));
            }
        }
        return list;
    }

    public boolean addDoctor(Doctor doc) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Doctors (id, name, specialty) VALUES (?, ?, ?)";
        try (Connection conn = Config.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, doc.getId());
            pstm.setString(2, doc.getName());
            pstm.setString(3, doc.getSpecialty());
            return pstm.executeUpdate() > 0;
        }
    }

    public void getSpecialtyStatistics() throws SQLException, ClassNotFoundException {
        String sql = "SELECT specialty, COUNT(*) as total FROM Doctors GROUP BY specialty";
        try (Connection conn = Config.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.printf("%-20s | %-10s\n", "Chuyên khoa", "Số lượng");
            while (rs.next()) {
                System.out.printf("%-20s | %-10d\n", rs.getString("specialty"), rs.getInt("total"));
            }
        }
    }
}