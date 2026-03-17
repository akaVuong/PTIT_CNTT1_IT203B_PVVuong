package bai6;
//STRATEGY: Discount
interface DiscountStrategy {
    double applyDiscount(double amount);
}

//STRATEGY: Payment
interface PaymentMethod {
    void pay(double amount);
}

//DIP: Notification
interface NotificationService {
    void send(String message);
}

//ABSTRACT FACTORY
abstract class SalesChannelFactory {
    abstract DiscountStrategy createDiscountStrategy();
    abstract PaymentMethod createPaymentMethod();
    abstract NotificationService createNotificationService();
}
