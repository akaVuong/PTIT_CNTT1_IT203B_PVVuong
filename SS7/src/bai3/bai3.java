package bai3;

interface PaymentMethod {
    void pay(double amount);
}

//Chỉ dành cho thanh toán COD
interface CODPayable extends PaymentMethod {
}

//Chỉ dành cho thanh toán thẻ tín dụng
interface CardPayable extends PaymentMethod {
}

//Dành cho ví điện tử (MoMo)
interface EWalletPayable extends PaymentMethod {
}

// Xử lý thanh toán COD
class CODPayment implements CODPayable {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán COD: " + amount + " - Thành công");
    }
}

//Xử lý thanh toán thẻ tín dụng
class CreditCardPayment implements CardPayable {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + amount + " - Thành công");
    }
}

//Xử lý thanh toán ví MoMo
class MomoPayment implements EWalletPayable {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + amount + " - Thành công");
    }
}

//Class PaymentProcessor Nhận PaymentMethod (interface tổng quát)chứng minh LSP: có thể thay thế các implementation
class PaymentProcessor {
    private PaymentMethod paymentMethod;
    // constructor nhận implementation
    public PaymentProcessor(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    // thực hiện thanh toán
    public void process(double amount) {
        paymentMethod.pay(amount);
    }
}

public class bai3 {
    public static void main(String[] args) {
        // Thanh toán COD
        PaymentProcessor cod =
                new PaymentProcessor(new CODPayment());
        cod.process(500000);
        // Thanh toán thẻ tín dụng
        PaymentProcessor card =
                new PaymentProcessor(new CreditCardPayment());
        card.process(1000000);

        // Thanh toán MoMo
        PaymentProcessor momo =
                new PaymentProcessor(new MomoPayment());
        momo.process(750000);

        // Kiểm tra LSP
        // Thay CreditCardPayment bằng MomoPayment
        PaymentMethod test = new MomoPayment();
        PaymentProcessor processor = new PaymentProcessor(test);
        System.out.println("Kiểm tra LSP:");
        processor.process(200000);
    }
}
