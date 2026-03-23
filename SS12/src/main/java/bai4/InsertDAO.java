package bai4;

import java.sql.*;
import java.util.List;

public class InsertDAO {

    public void insertWithPrepared(Connection conn, List<TestResult> list) {
        try {
            String sql = "INSERT INTO Results(data) VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (TestResult tr : list) {
                pstmt.setString(1, tr.getData());
                pstmt.executeUpdate();
            }

            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
