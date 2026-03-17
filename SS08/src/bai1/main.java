package bai1;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Device device = null;          // lưu thiết bị hiện tại
        DeviceFactory factory = null;  // factory tương ứng

        while (true) {
            // menu
            System.out.println("\nMENU");
            System.out.println("1. Kết nối phần cứng");
            System.out.println("2. Tạo thiết bị");
            System.out.println("3. Bật thiết bị");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // gọi Singleton (chỉ tạo 1 lần)
                    HardwareConnection.getInstance();
                    break;

                case 2:
                    // chọn loại thiết bị
                    System.out.println("Chọn loại: 1. Đèn  2. Quạt  3. Điều hòa");
                    int type = sc.nextInt();
                    // chọn factory tương ứng
                    switch (type) {
                        case 1:
                            factory = new LightFactory();
                            break;
                        case 2:
                            factory = new FanFactory();
                            break;
                        case 3:
                            factory = new AirConditionerFactory();
                            break;
                        default:
                            System.out.println("Sai lựa chọn!");
                            continue;
                    }
                    // tạo device bằng factory
                    device = factory.createDevice();
                    break;
                case 3:
                    // bật thiết bị
                    if (device != null) {
                        device.turnOn();
                    } else {
                        System.out.println("Chưa tạo thiết bị!");
                    }
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
