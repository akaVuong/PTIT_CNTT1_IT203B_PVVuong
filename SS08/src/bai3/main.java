package bai3;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();
        //UNDO
        UndoManager undoManager = new UndoManager();
        //REMOTE
        RemoteControl remote = new RemoteControl(5, undoManager);
        while (true) {
            System.out.println("\n=MENU=");
            System.out.println("1. Gán nút");
            System.out.println("2. Nhấn nút");
            System.out.println("3. Undo");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Chọn nút (1-5): ");
                    int btn = sc.nextInt() - 1;

                    System.out.println("Chọn lệnh:");
                    System.out.println("1. Bật đèn");
                    System.out.println("2. Tắt đèn");
                    System.out.println("3. Bật quạt");
                    System.out.println("4. Tắt quạt");
                    System.out.println("5. Set điều hòa");
                    int cmd = sc.nextInt();
                    Command command = null;
                    switch (cmd) {
                        case 1:
                            command = new LightOnCommand(light);
                            break;
                        case 2:
                            command = new LightOffCommand(light);
                            break;
                        case 3:
                            command = new FanOnCommand(fan);
                            break;
                        case 4:
                            command = new FanOffCommand(fan);
                            break;
                        case 5:
                            System.out.print("Nhập nhiệt độ: ");
                            int temp = sc.nextInt();
                            command = new ACSetTemperatureCommand(ac, temp);
                            break;
                        default:
                            System.out.println("Sai lệnh!");
                            continue;
                    }

                    remote.setCommand(btn, command);
                    break;
                case 2:
                    System.out.print("Nhấn nút (1-5): ");
                    int press = sc.nextInt() - 1;
                    remote.pressButton(press);
                    break;
                case 3:
                    undoManager.undo();
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
