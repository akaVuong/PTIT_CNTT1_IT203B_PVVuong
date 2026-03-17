package bai5;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //TẠO OBJECT
        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();
        TemperatureSensor sensor = new TemperatureSensor();
        // đăng ký observer
        sensor.attach(fan);
        sensor.attach(ac);
        // command sleep mode
        Command sleepCommand = new SleepModeCommand(light, fan, ac);
        while (true) {
            System.out.println("\nMENU");
            System.out.println("1. Kích hoạt chế độ ngủ");
            System.out.println("2. Thay đổi nhiệt độ");
            System.out.println("3. Xem trạng thái");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // chạy macro command
                    sleepCommand.execute();
                    break;

                case 2:
                    System.out.print("Nhập nhiệt độ: ");
                    int temp = sc.nextInt();

                    // sensor thay đổi -> observer tự chạy
                    sensor.setTemperature(temp);
                    break;

                case 3:
                    // xem trạng thái hiện tại
                    fan.status();
                    ac.status();
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
