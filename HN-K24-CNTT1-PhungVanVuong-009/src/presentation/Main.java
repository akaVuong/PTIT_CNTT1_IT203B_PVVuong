package presentation;

import business.Playerbusiness;
import entity.ChessPlayer;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Playerbusiness PBS = Playerbusiness.getInstance();
        while (true){
            System.out.println("MENU");
            System.out.println("1. hiển thị danh sach các cờ thủ");
            System.out.println("2. thêm mới cờ thử");
            System.out.println("3. cập nhật thông tim sinh viên theo mã cờ thủ");
            System.out.println("4. xóa sinh viên theo mã cờ thủ");
            System.out.println("5. tìm kiếm cờ thủ theo tên");
            System.out.println("6. lọc danh sách cờ thử xuất sắc(elo > 1500)");
            System.out.println("7. sắp xếp danh sách sinh viên giảm dần theo elo");
            System.out.println("8. thoát chương trình");
            System.out.print("lựa chọn của bạn: ");
            int choice;
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    PBS.displaylist();
                    break;
                case 2:
                    ChessPlayer user = new ChessPlayer();
                    user.inputData(scanner);
                    PBS.addPlayer(user);
                    break;
                case 3:
                    System.out.println("nhập Id: ");
                    break;
                case 4:
                    System.out.println("nhập id: ");
                    PBS.deletePlayer(scanner.nextLine());
                    break;
                case 5:
                    System.out.println(" nhập tên cờ thủ muốn tìm");
                    PBS.searchPlayer(scanner.nextLine());
                    break;
                case 6:
                    break;
                case 7:
                    PBS.sortPlayerByElo();
                    break;
                case 8:
                    System.out.println("thoát chương trình");
                    return ;
                default:
                    System.out.println("lựa chọn không hợp lệ");
            }
        }
    }
}
