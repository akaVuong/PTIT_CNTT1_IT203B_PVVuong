package bai4;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            //dùng DBConnection của bạn
            Connection conn = DBConnection.getConnection();

            // 🧪 tạo dữ liệu
            List<TestResult> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                list.add(new TestResult("Data_" + i));
            }

            // xóa dữ liệu cũ
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM Results");
            st.close();

            // gọi DAO
            InsertDAO dao = new InsertDAO();

            long start = System.currentTimeMillis();

            dao.insertWithPrepared(conn, list);

            long end = System.currentTimeMillis();

            System.out.println("Time: " + (end - start) + " ms");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}