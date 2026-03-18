package Entity;
import java.util.*;
public class main {
    public  static  void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ProductDatabase db = ProductDatabase.getInstance();

        while (true){
            System.out.println("quan ly");
            System.out.println("1 them moi");
            System.out.println("2 xem danh sach");
            System.out.println("3 cap nhat");
            System.out.println("4 xoa");
            System.out.println("5 thoat");
            System.out.print("lua chon cua ban: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("nhap sai dinh dang");
                continue;
            }
             switch (choice){
                 case 1:
                     break;
                 case 2:
                     break;
                 case 3:
                     break;
                 case 4:
                     break;
                 case 5:
                     System.out.println("thoat chuong trinh");
                     break;
                 default:
                     System.out.println("lua chon khong hop le");
             }
        }
    }
}
