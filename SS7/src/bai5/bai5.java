package bai5;

import java.util.*;

/* Product model */
class Product {
    String id;
    String name;
    double price;
    String category;

    public Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

/* Customer model */
class Customer {
    String name;
    String email;
    String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}

/* OrderItem */
class OrderItem {
    Product product;
    int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double getTotal() {
        return product.price * quantity;
    }
}

/* Order */
class Order {
    String id;
    Customer customer;
    List<OrderItem> items = new ArrayList<>();
    double finalAmount;

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}

/*
 =========================
 DISCOUNT STRATEGY (OCP)
 =========================
*/

interface DiscountStrategy {
    double apply(double total);
}

class PercentageDiscount implements DiscountStrategy {
    double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    public double apply(double total) {
        return total - (total * percent / 100);
    }
}

class FixedDiscount implements DiscountStrategy {
    double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    public double apply(double total) {
        return total - amount;
    }
}

class HolidayDiscount implements DiscountStrategy {
    public double apply(double total) {
        return total * 0.85;
    }
}

/*
 =========================
 PAYMENT METHOD
 =========================
*/

interface PaymentMethod {
    void pay(double amount);
}

class CODPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán COD: " + amount);
    }
}

class CreditCardPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán thẻ tín dụng: " + amount);
    }
}

class MomoPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán MoMo: " + amount);
    }
}

class VNPayPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán VNPay: " + amount);
    }
}

/*
 =========================
 ORDER REPOSITORY (DIP)
 =========================
*/

interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}

class FileOrderRepository implements OrderRepository {

    List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        orders.add(order);
        System.out.println("Đã lưu đơn hàng: " + order.id);
    }

    public List<Order> findAll() {
        return orders;
    }
}

/*
 =========================
 NOTIFICATION SERVICE
 =========================
*/

interface NotificationService {
    void send(String message);
}

class EmailNotification implements NotificationService {
    public void send(String message) {
        System.out.println("Gửi Email: " + message);
    }
}

class SMSNotification implements NotificationService {
    public void send(String message) {
        System.out.println("Gửi SMS: " + message);
    }
}

/*
 =========================
 INVOICE GENERATOR
 =========================
*/

class InvoiceGenerator {

    static void print(Order order) {

        System.out.println("\n===== HÓA ĐƠN =====");
        System.out.println("Khách: " + order.customer.name);

        for (OrderItem item : order.items) {

            System.out.println(
                    item.product.name +
                            " x " + item.quantity +
                            " = " + item.getTotal());
        }

        System.out.println("Tổng tiền: " + order.finalAmount);
    }
}

/*
 =========================
 ORDER SERVICE (DIP)
 =========================
*/

class OrderService {

    OrderRepository repository;
    NotificationService notification;

    public OrderService(OrderRepository repo, NotificationService noti) {
        repository = repo;
        notification = noti;
    }

    void createOrder(Order order) {

        repository.save(order);

        notification.send("Đơn hàng " + order.id + " đã được tạo");
    }
}

/*
 =========================
 MAIN PROGRAM
 =========================
*/

public class bai5 {

    static List<Product> products = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();

    static Map<Integer, PaymentMethod> payments = new HashMap<>();
    static Map<Integer, DiscountStrategy> discounts = new HashMap<>();

    static OrderRepository repository = new FileOrderRepository();
    static NotificationService notification = new EmailNotification();

    static OrderService orderService =
            new OrderService(repository, notification);

    static int orderCounter = 1;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        /* default discount */
        discounts.put(1, new PercentageDiscount(10));
        discounts.put(2, new FixedDiscount(50000));
        discounts.put(3, new HolidayDiscount());

        /* default payment */
        payments.put(1, new CODPayment());
        payments.put(2, new CreditCardPayment());
        payments.put(3, new MomoPayment());
        payments.put(4, new VNPayPayment());

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("4. Xem danh sách đơn hàng");
            System.out.println("5. Tính doanh thu");
            System.out.println("6. Thêm phương thức thanh toán");
            System.out.println("7. Thêm chiến lược giảm giá");
            System.out.println("8. Thoát");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Mã: ");
                    String id = sc.nextLine();

                    System.out.print("Tên: ");
                    String name = sc.nextLine();

                    System.out.print("Giá: ");
                    double price = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Danh mục: ");
                    String cate = sc.nextLine();

                    products.add(new Product(id, name, price, cate));

                    System.out.println("Đã thêm sản phẩm " + id);
                    break;

                case 2:

                    System.out.print("Tên: ");
                    String cname = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    customers.add(new Customer(cname, email, phone));

                    System.out.println("Đã thêm khách hàng");
                    break;

                case 3:

                    if (products.isEmpty() || customers.isEmpty()) {
                        System.out.println("Cần thêm sản phẩm và khách hàng trước");
                        break;
                    }

                    Customer customer = customers.get(0);
                    Product product = products.get(0);

                    Order order = new Order(
                            "ORD00" + orderCounter++, customer);

                    order.items.add(new OrderItem(product, 1));

                    double total = order.getTotal();

                    DiscountStrategy discount = discounts.get(1);
                    order.finalAmount = discount.apply(total);

                    PaymentMethod payment = payments.get(2);
                    payment.pay(order.finalAmount);

                    InvoiceGenerator.print(order);

                    orderService.createOrder(order);

                    break;

                case 4:

                    for (Order o : repository.findAll()) {

                        System.out.println(
                                o.id + " - " +
                                        o.customer.name +
                                        " - " + o.finalAmount);
                    }

                    break;

                case 5:

                    double revenue = 0;

                    for (Order o : repository.findAll()) {
                        revenue += o.finalAmount;
                    }

                    System.out.println("Tổng doanh thu: " + revenue);

                    break;

                case 6:

                    System.out.println("Đã thêm thanh toán ZaloPay");

                    payments.put(5,
                            amount -> System.out.println("Thanh toán ZaloPay: " + amount));

                    break;

                case 7:

                    System.out.println("Đã thêm giảm giá VIP 20%");

                    discounts.put(4, totalAmount -> totalAmount * 0.8);

                    break;

                case 8:
                    System.exit(0);
            }
        }
    }
}
