package bai6;

import java.util.*;

interface DiscountStrategy {
    double apply(double total);
}

class WebsiteDiscount implements DiscountStrategy {
    public double apply(double total) {
        System.out.println("Áp dụng giảm giá 10% cho đơn hàng Website");
        return total * 0.9;
    }
}

class MobileDiscount implements DiscountStrategy {
    public double apply(double total) {
        System.out.println("Áp dụng giảm giá 15% cho lần đầu MobileApp");
        return total * 0.85;
    }
}

class StoreDiscount implements DiscountStrategy {

    public double apply(double total) {
        System.out.println("Giảm giá thành viên tại StorePOS");
        return total * 0.95;
    }
}





interface PaymentMethod {
    void pay(double amount);
}

class CreditCardPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng qua cổng thanh toán online");
    }
}

class MomoPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo tích hợp");
    }
}

class CashPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán tiền mặt tại cửa hàng");
    }
}





interface NotificationService {
    void send(String message);
}

class EmailNotification implements NotificationService {

    public void send(String message) {
        System.out.println("Gửi email xác nhận: " + message);
    }
}

class PushNotification implements NotificationService {

    public void send(String message) {
        System.out.println("Gửi push notification: " + message);
    }
}

class PrintReceipt implements NotificationService {

    public void send(String message) {
        System.out.println("In hóa đơn giấy: " + message);
    }
}






interface SalesChannelFactory {

    DiscountStrategy createDiscount();

    PaymentMethod createPayment();

    NotificationService createNotification();
}





class WebsiteFactory implements SalesChannelFactory {

    public DiscountStrategy createDiscount() {
        return new WebsiteDiscount();
    }

    public PaymentMethod createPayment() {
        return new CreditCardPayment();
    }

    public NotificationService createNotification() {
        return new EmailNotification();
    }
}





class MobileFactory implements SalesChannelFactory {

    public DiscountStrategy createDiscount() {
        return new MobileDiscount();
    }

    public PaymentMethod createPayment() {
        return new MomoPayment();
    }

    public NotificationService createNotification() {
        return new PushNotification();
    }
}





class StoreFactory implements SalesChannelFactory {

    public DiscountStrategy createDiscount() {
        return new StoreDiscount();
    }

    public PaymentMethod createPayment() {
        return new CashPayment();
    }

    public NotificationService createNotification() {
        return new PrintReceipt();
    }
}





class Order {

    double total;

    public Order(double total) {
        this.total = total;
    }
}





class OrderService {

    SalesChannelFactory factory;

    public OrderService(SalesChannelFactory factory) {
        this.factory = factory;
    }

    void processOrder(double total) {

        DiscountStrategy discount = factory.createDiscount();
        PaymentMethod payment = factory.createPayment();
        NotificationService notify = factory.createNotification();

        double finalAmount = discount.apply(total);

        payment.pay(finalAmount);

        notify.send("Đơn hàng thành công");

        System.out.println("Tổng thanh toán: " + finalAmount);
    }
}



public class bai6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== CHỌN KÊNH BÁN ===");
            System.out.println("1. Website");
            System.out.println("2. Mobile App");
            System.out.println("3. Store POS");
            System.out.println("4. Thoát");
            int choice = sc.nextInt();
            SalesChannelFactory factory = null;
            switch (choice) {
                case 1:
                    factory = new WebsiteFactory();
                    System.out.println("Bạn đã chọn kênh Website");
                    break;
                case 2:
                    factory = new MobileFactory();
                    System.out.println("Bạn đã chọn kênh Mobile App");
                    break;
                case 3:
                    factory = new StoreFactory();
                    System.out.println("Bạn đã chọn kênh Store POS");
                    break;
                case 4:
                    return;
            }

            OrderService orderService = new OrderService(factory);

            System.out.print("Nhập giá sản phẩm: ");
            double price = sc.nextDouble();

            orderService.processOrder(price);
        }
    }
}
