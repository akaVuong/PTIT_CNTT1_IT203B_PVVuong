package bai6;
// Giảm 10%
class WebDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.9;
    }
}
// Giảm 15% (lần đầu)
class FirstTimeDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.85;
    }
}

// Giảm 5%
class MemberDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.95;
    }
}

//PAYMENT
class CreditCardPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán bằng Credit Card: " + amount);
    }
}

class MoMoPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán bằng MoMo: " + amount);
    }
}

class CODPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán khi nhận hàng (COD): " + amount);
    }
}

//NOTIFICATION
class EmailNotification implements NotificationService {
    public void send(String message) {
        System.out.println("Gửi Email: " + message);
    }
}

class PushNotification implements NotificationService {
    public void send(String message) {
        System.out.println("Gửi Push Notification: " + message);
    }
}

class PrintNotification implements NotificationService {
    public void send(String message) {
        System.out.println("In hóa đơn: " + message);
    }
}

//FACTORY
// Website
class WebsiteFactory extends SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() {
        return new WebDiscount();
    }

    public PaymentMethod createPaymentMethod() {
        return new CreditCardPayment();
    }

    public NotificationService createNotificationService() {
        return new EmailNotification();
    }
}

// Mobile App
class MobileAppFactory extends SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() {
        return new FirstTimeDiscount();
    }

    public PaymentMethod createPaymentMethod() {
        return new MoMoPayment();
    }

    public NotificationService createNotificationService() {
        return new PushNotification();
    }
}

// POS (cửa hàng)
class POSFactory extends SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() {
        return new MemberDiscount();
    }

    public PaymentMethod createPaymentMethod() {
        return new CODPayment();
    }

    public NotificationService createNotificationService() {
        return new PrintNotification();
    }
}

//ORDER SERVICE
class OrderService {
    private DiscountStrategy discount;
    private PaymentMethod payment;
    private NotificationService notification;

    // nhận factory -> DIP
    public OrderService(SalesChannelFactory factory) {
        // tạo các object từ factory
        this.discount = factory.createDiscountStrategy();
        this.payment = factory.createPaymentMethod();
        this.notification = factory.createNotificationService();
    }

    public void placeOrder(String product, double price, int quantity) {
        double total = price * quantity;

        // áp dụng giảm giá
        double finalPrice = discount.applyDiscount(total);
        System.out.println("Áp dụng giảm giá: " + finalPrice);

        // thanh toán
        payment.pay(finalPrice);

        // thông báo
        notification.send("Đơn hàng thành công");
    }
}
