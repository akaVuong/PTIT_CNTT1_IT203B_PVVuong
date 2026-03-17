package bai4;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //TẠO SUBJECT
        TemperatureSensor sensor = new TemperatureSensor();
        // TẠO OBSERVERS
        Fan fan = new Fan();
        Humidifier humidifier = new Humidifier();
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Đăng ký thiết bị");
            System.out.println("2. Set nhiệt độ");
            System.out.println("3. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // đăng ký observer
                    sensor.attach(fan);
                    System.out.println("Quạt: Đã đăng ký nhận thông báo");

                    sensor.attach(humidifier);
                    System.out.println("Máy tạo ẩm: Đã đăng ký");
                    break;

                case 2:
                    // thay đổi nhiệt độ
                    System.out.print("Nhập nhiệt độ: ");
                    int temp = sc.nextInt();

                    // khi set -> tự notify
                    sensor.setTemperature(temp);
                    break;

                case 3:
                    System.out.println("Thoát...");
                    return;

                default:
                    System.out.println("Sai lựa chọn!");
            }
        }
    }
}
