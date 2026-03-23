package Bai5;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = Config.getConnection();
        DoctorDao dao = new DoctorDao();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- HỆ THỐNG RIKKEI-CARE ---");
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ mới");
            System.out.println("3. Thống kê chuyên khoa");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        dao.getAllDoctors().forEach(d ->
                                System.out.println(d.getId() + " - " + d.getName() + " - " + d.getSpecialty()));
                        break;
                    case 2:
                        System.out.print("Nhập mã: "); String id = sc.nextLine();
                        System.out.print("Nhập tên: "); String name = sc.nextLine();
                        System.out.print("Nhập chuyên khoa: "); String spec = sc.nextLine();
                        if (dao.addDoctor(new Doctor(id, name, spec))) System.out.println("Thêm thành công!");
                        break;
                    case 3:
                        dao.getSpecialtyStatistics();
                        break;
                    case 4:
                        System.exit(0);
                }
            } catch (Exception e) {
                System.err.println("LỖI: " + e.getMessage());
            }
        }
    }
}