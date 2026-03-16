package bai2;


//Định nghĩa phương thức áp dụng giảm giá
interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

//Giảm giá theo phần trăm (ví dụ 10%)
class PercentageDiscount implements DiscountStrategy {
    private double percent;
    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * percent / 100);
    }
}

//Giảm giá theo số tiền cố định
class FixedDiscount implements DiscountStrategy {
    private double amount;
    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - amount;
    }
}

//Không giảm giá
class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}

/*HolidayDiscount Giảm giá ngày lễ (15%) Thêm class mới mà không cần sửa code cũ → đúng OCP
 */
class HolidayDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * 15 / 100);
    }
}

//Lớp tính tiền đơn hàng Nhận DiscountStrategy thông qua constructor
class OrderCalculator {
    private DiscountStrategy discountStrategy;
    public OrderCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    // tính số tiền sau khi áp dụng giảm giá
    public double calculate(double totalAmount) {
        return discountStrategy.applyDiscount(totalAmount);
    }
}

public class bai2 {

    public static void main(String[] args) {

        double total = 1_000_000;

        // áp dụng giảm giá 10%
        OrderCalculator order1 =
                new OrderCalculator(new PercentageDiscount(10));
        System.out.println("Sau giảm 10%: " + order1.calculate(total));

        // giảm giá cố định 50.000
        OrderCalculator order2 =
                new OrderCalculator(new FixedDiscount(50000));
        System.out.println("Sau giảm 50.000: " + order2.calculate(total));

        // không giảm giá
        OrderCalculator order3 =
                new OrderCalculator(new NoDiscount());
        System.out.println("Không giảm giá: " + order3.calculate(total));

        // thêm loại giảm giá mới (HolidayDiscount 15%)
        OrderCalculator order4 =
                new OrderCalculator(new HolidayDiscount());
        System.out.println("Giảm giá Holiday 15%: " + order4.calculate(total));
    }
}
