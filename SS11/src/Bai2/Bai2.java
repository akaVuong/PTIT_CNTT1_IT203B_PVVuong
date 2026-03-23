package Bai2;

// Phần 1 : Phân tích
// Lệnh if (rs.next()) chỉ kiểm tra và di chuyển con trỏ đến bản ghi đầu tiên trong tập kết quả (nếu có).
// Khi một ResultSet được tạo ra, con trỏ (cursor) mặc định nằm ở vị trí trước dòng đầu tiên (Position 0 - Before First).
// Lần gọi next() đầu tiên: Con trỏ di chuyển từ vị trí 0 xuống dòng 1. Nếu dòng 1 tồn tại, hàm trả về true.
// Các lần gọi next() tiếp theo: Con trỏ tiếp tục nhảy xuống dòng 2, dòng 3... mỗi lần gọi là một lần dịch chuyển xuống dưới.
// Khi đến dòng cuối cùng: Nếu bạn gọi next() thêm một lần nữa khi đã ở dòng cuối, con trỏ sẽ nhảy ra ngoài phạm vi dữ liệu và hàm trả về false. Đây chính là "điểm dừng" để thoát vòng lặp.


import java.sql.*;
import java.util.concurrent.locks.StampedLock;

// Phần 2 : Thực thi
public class Bai2 {
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASS = "123";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    static void main(String[] args) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        Connection conn = getConnection();
        ResultSet rs = stmt.executeQuery("SELECT medicine_name,stock FROM Pharmacy");

        while (rs.next()) {
            System.out.println("Thuốc : " + rs.getString("medicine_name"));
        }
    }
}
