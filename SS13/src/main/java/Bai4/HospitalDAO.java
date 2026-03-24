package Bai4;

import java.sql.*;
import java.util.*;

public class HospitalDAO {

    public List<BenhNhanDTO> getAllBenhNhan() {
        List<BenhNhanDTO> list = new ArrayList<>();
        Map<Integer, BenhNhanDTO> map = new HashMap<>();
        String sql = "SELECT bn.maBenhNhan, bn.tenBenhNhan, " +
                "dv.maDichVu, dv.tenDichVu " +
                "FROM BenhNhan bn " +
                "LEFT JOIN DichVuSuDung dv ON bn.maBenhNhan = dv.maBenhNhan";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int maBN = rs.getInt("maBenhNhan");

                // Nếu chưa có → tạo mới
                if (!map.containsKey(maBN)) {
                    String tenBN = rs.getString("tenBenhNhan");
                    BenhNhanDTO bn = new BenhNhanDTO(maBN, tenBN);
                    map.put(maBN, bn);
                }

                //  BẪY 2
                // Nếu bệnh nhân chưa có dịch vụ → dv sẽ NULL
                int maDV = rs.getInt("maDichVu");

                if (!rs.wasNull()) { // tránh NullPointerException
                    String tenDV = rs.getString("tenDichVu");
                    DichVu dv = new DichVu(maDV, tenDV);

                    map.get(maBN).getDsDichVu().add(dv);
                }
            }

            list.addAll(map.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
