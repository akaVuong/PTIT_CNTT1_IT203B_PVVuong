package Bai5;

import java.util.Scanner;

public class ReceptionView {
    private Scanner sc = new Scanner(System.in);
    private ReceptionController controller = new ReceptionController();

    public void menu() {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Xem giường trống");
            System.out.println("2. Tiếp nhận bệnh nhân");
            System.out.println("3. Thoát");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 2:
                    admit();
                    break;
                case 3:
                    return;
            }
        }
    }

    private void admit() {
        try {
            System.out.print("Tên: ");
            String name = sc.nextLine();

            System.out.print("Tuổi: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Mã giường: ");
            int bedId = Integer.parseInt(sc.nextLine());

            System.out.print("Tiền: ");
            double money = Double.parseDouble(sc.nextLine());

            controller.admitPatient(name, age, bedId, money);

        } catch (Exception e) {
            System.out.println(" Nhập sai dữ liệu!");
        }
    }
}
