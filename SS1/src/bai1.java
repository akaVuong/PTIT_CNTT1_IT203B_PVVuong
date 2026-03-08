import java.util.Scanner;
public class bai1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Nhập một số nguyên: ");
            String input = scanner.nextLine();
            //phân tích dữ liệu
            int number = Integer.parseInt(input);
            System.out.println("Bạn đã nhập số: " + number);
        } catch (NumberFormatException e) {
            // Bắt lỗi nếu nhập chữ thay vì số
            System.out.println("Lỗi: Bạn phải nhập một số hợp lệ!");
        } finally {
            //Luôn thực hiện
            scanner.close();
            System.out.println("dọn dẹp tài nguyên");
        }
    }
}