package bai6;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SalesChannelFactory factory = null;
        while (true) {
            System.out.println("\nMENU");
            System.out.println("1. Website");
            System.out.println("2. Mobile App");
            System.out.println("3. POS");
            System.out.println("4. Thoát");
            System.out.print("Chọn kênh: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    factory = new WebsiteFactory();
                    System.out.println("Bạn đã chọn kênh Website");
                    break;

                case 2:
                    factory = new MobileAppFactory();
                    System.out.println("Bạn đã chọn kênh Mobile App");
                    break;

                case 3:
                    factory = new POSFactory();
                    System.out.println("Bạn đã chọn kênh POS");
                    break;

                case 4:
                    System.out.println("Thoát...");
                    return;

                default:
                    System.out.println("Sai lựa chọn!");
                    continue;
            }
            // tạo order service theo factory
            OrderService order = new OrderService(factory);
            // nhập đơn hàng
            sc.nextLine(); // clear buffer
            System.out.print("Nhập sản phẩm: ");
            String product = sc.nextLine();
            System.out.print("Nhập giá: ");
            double price = sc.nextDouble();
            System.out.print("Nhập số lượng: ");
            int qty = sc.nextInt();
            // xử lý đơn hàng
            order.placeOrder(product, price, qty);
        }
    }
}
