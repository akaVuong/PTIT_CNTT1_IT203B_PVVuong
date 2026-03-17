package bai2;

// Main.java

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //TẠO ADAPTER
        OldThermometer oldThermometer = new OldThermometer();
        // bọc lại bằng adapter để dùng được interface mới
        TemperatureSensor sensor = new ThermometerAdapter(oldThermometer);
        //TẠO THIẾT BỊ
        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();
        // FACADE
        SmartHomeFacade home = new SmartHomeFacade(light, fan, ac, sensor);
        while (true) {
            System.out.println("\n MENU");
            System.out.println("1. Xem nhiệt độ");
            System.out.println("2. Chế độ rời nhà");
            System.out.println("3. Chế độ ngủ");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // lấy nhiệt độ từ adapter
                    double temp = home.getCurrentTemperature();
                    System.out.printf("Nhiệt độ hiện tại: %.1f°C\n", temp);
                    break;

                case 2:
                    // gọi facade
                    home.leaveHome();
                    break;

                case 3:
                    // gọi facade
                    home.sleepMode();
                    break;

                case 4:
                    System.out.println("Thoát...");
                    return;

                default:
                    System.out.println("Sai lựa chọn!");
            }
        }
    }
}
