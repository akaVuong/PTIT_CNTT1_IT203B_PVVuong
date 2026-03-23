package Bai1;

// Phần 1 : Phân tích
// Cạn kiệt tài nguyên
// Rò rỉ bộ nhớ
// Hiệu năng giảm sút
// Tính sẵn sàng thấp

// Phần 2 : Thực thi
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bai1 {
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASS = "123";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    static void main(String[] args) {
        Connection conn = null;
        try {
            conn = getConnection();
            System.out.println("Kết nối thành công DB : Đào Trường Sơn!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    System.out.println("Đã đóng kết nối an toàn.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
