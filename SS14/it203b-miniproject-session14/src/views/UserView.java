package views;

import models.User;
import services.UserService;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private UserService userService;
    private Scanner sc = new Scanner(System.in);
    public UserView(UserService userService) {
        this.userService = userService;
    }

    public void menu() {
        while (true) {
            System.out.println("\n1. Thêm user");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine()); // chuỗi thành số nguyên
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    showUsers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Sai lựa chọn");
            }
        }
    }

    // nhập dữ liệu từ người dùng rồi gọi service
    private void addUser() {
        System.out.print("Tên: ");
        String name = sc.nextLine();
        System.out.print("Địa chỉ: ");
        String address = sc.nextLine();
        // tạo object user từ input
        User user = new User(0, name, address);
        // gọi service xử lý
        if (userService.addUser(user)) {
            System.out.println("Thêm thành công");
        } else {
            System.out.println("Thêm thất bại");
        }
    }

    //lấy danh sách từ service in ra màn hình
    private void showUsers() {
        List<User> list = userService.getAllUsers();
        System.out.println("\nDanh sách user:");
        // duyệt list và in ra
        for (User u : list) {
            System.out.println(
                    u.getUserId() + " - " + u.getFullName() + " - " + u.getAddress()
            );
        }
    }
}