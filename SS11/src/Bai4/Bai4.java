package Bai4;

// phần 1 : phân tích
// Khi hacker nhập chuỗi '' OR '1'='1', đoạn mã thực hiện nối chuỗi trực tiếp vào câu lệnh SQL ban đầu

import java.sql.*;

public class Bai4 {
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASS = "123";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static String sanitizeInput(String input) {
        if (input == null) return "";
        String cleanInput = input.replace("'", "''");
        cleanInput = cleanInput.replace("--", "");
        cleanInput = cleanInput.replace(";", "");
        return cleanInput;
    }

    public static void main(String[] args) {
        String patientNameInput = "'' OR '1'='1'";
        String safePatientName = sanitizeInput(patientNameInput);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM Patients WHERE full_name = '" + safePatientName + "'";

            System.out.println("Câu lệnh SQL thực thi: " + sql);

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Patient: " + rs.getString("full_name"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
