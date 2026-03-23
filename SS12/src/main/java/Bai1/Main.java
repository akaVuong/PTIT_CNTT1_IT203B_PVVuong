package Bai1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DoctorDAO dao = new DoctorDAO();

        System.out.print("Nhập mã bác sĩ: ");
        String code = sc.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String pass = sc.nextLine();

        boolean result = dao.login(code, pass);

        if (result) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu!");
        }
    }
}
