package Bai3;

//  Phần 1 : Phân tích
// Giá trị trả về để xác định trạng thái sau khi thực hiện câu lệnh SQL
//  Giá trị = 1 : Thành công, = 0 : Thất bại , = -1 : không tồn tại


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Phần 2 :
public class Bai3 {
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

        int inputId = 1;
        int rs = stmt.executeUpdate("UPDATE Beds SET bed_status = 'Occupied' WHERE bed_id = " + inputId);
        if( rs == 1){
            System.out.println("Cập nhật giường bệnh thành công!");
        }else if (rs == 0){
            System.out.println("Cập nhật giường bệnh thất bại!");
        }else{
            System.out.println("Id không tồn tại!");
        }
    }
}
