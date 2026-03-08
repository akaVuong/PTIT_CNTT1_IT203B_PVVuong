import java.util.Scanner;
public class bai2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tổng số người: ");
        int tongSoNguoi = scanner.nextInt();
        System.out.print("Nhập số nhóm: ");
        int soNhom = scanner.nextInt();
        try {
            // Vùng nguy hiểm
            int ketQua = tongSoNguoi / soNhom;
            System.out.println("Mỗi nhóm có: " + ketQua + " người");

        } catch (ArithmeticException e) {
            // Bắt lỗi chia cho 0
            System.out.println("Không thể chia cho 0!");
        }
        System.out.println("Chương trình vẫn tiếp tục chạy sau khối try-catch.");
        scanner.close();
    }
}