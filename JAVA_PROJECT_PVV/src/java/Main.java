import model.User;
import service.AuthService;
import presentation.AdminMenu;
import presentation.EmployeeMenu;
import presentation.SupportMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();

        while (true) {
            System.out.println("\n=== HỆ THỐNG QUẢN LÝ PHÒNG HỌP ===");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký (Nhân viên)");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                System.out.print("Username: "); String u = sc.nextLine().trim();
                System.out.print("Password: "); String p = sc.nextLine().trim();
                User user = authService.login(u, p);

                if (user != null) {
                    System.out.println("\nChào " + user.getFullName() + " [" + user.getRole() + "]");
                    String role = user.getRole().toUpperCase();
                    if (role.equals("ADMIN")) {
                        new AdminMenu().display(user, sc);
                    } else if (role.equals("EMPLOYEE")) {
                        new EmployeeMenu().display(user, sc);
                    } else if (role.equals("SUPPORT")) {
                        new SupportMenu().display(user, sc);
                    }
                } else {
                    System.out.println(" Sai tài khoản hoặc mật khẩu!");
                }
            } else if (c.equals("2")) {
                try {
                    System.out.println("\n--- ĐĂNG KÝ TÀI KHOẢN NHÂN VIÊN ---");
                    System.out.print("User: "); String u = sc.nextLine();
                    System.out.print("Pass: "); String p = sc.nextLine();
                    System.out.print("Tên: "); String n = sc.nextLine();
                    System.out.print("ID Phòng ban (số): "); int d = Integer.parseInt(sc.nextLine());
                    System.out.print("SĐT: "); String ph = sc.nextLine();

                    // GỌI HÀM MỚI ĐỂ NHẬN THÔNG BÁO CHI TIẾT
                    String result = authService.registerNewUser(u, p, n, d, ph, "EMPLOYEE");
                    System.out.println(result);

                } catch (NumberFormatException e) {
                    System.out.println(" Lỗi: ID Phòng ban phải là một con số!");
                }
            } else if (c.equals("0")) {
                System.out.println("Cảm ơn bạn đã sử dụng hệ thống!");
                break;
            }
        }
    }
}