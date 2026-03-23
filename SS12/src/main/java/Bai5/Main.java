package Bai5;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DBConnection.getConnection();
            Scanner sc = new Scanner(System.in);
            PatientDAO dao = new PatientDAO();

            while (true) {
                System.out.println("\n===== RHMS MENU =====");
                System.out.println("1. Danh sách bệnh nhân");
                System.out.println("2. Thêm bệnh nhân");
                System.out.println("3. Cập nhật bệnh án");
                System.out.println("4. Xuất viện & tính phí");
                System.out.println("5. Thoát");

                System.out.print("Chọn: ");
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        dao.listPatients(conn);
                        break;
                    case 2:
                        dao.addPatient(conn, sc);
                        break;
                    case 3:
                        dao.updateDisease(conn, sc);
                        break;
                    case 4:
                        dao.discharge(conn, sc);
                        break;
                    case 5:
                        System.out.println("Thoát...");
                        conn.close();
                        return;
                    default:
                        System.out.println("Sai lựa chọn!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
