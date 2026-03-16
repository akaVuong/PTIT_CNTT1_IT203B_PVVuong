package bai1;

import java.util.*;

class Product {
    String id;
    String name;
    double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class Customer {
    String name;
    String email;
    String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}

class OrderItem {
    Product product;
    int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}


class Order {
    String orderId;
    Customer customer;
    List<OrderItem> items;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    // thêm sản phẩm vào đơn hàng
    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
    }
}


class OrderCalculator {
    public double calculateTotal(Order order) {
        double total = 0;
        for (OrderItem item : order.items) {
            total += item.product.price * item.quantity;
        }

        return total;
    }
}

//Chịu trách nhiệm lưu và tìm kiếm đơn hàng

class OrderRepository {
    Map<String, Order> orders = new HashMap<>();
    // lưu đơn hàng
    public void save(Order order) {
        orders.put(order.orderId, order);
        System.out.println("Đã lưu đơn hàng " + order.orderId);
    }

    // tìm đơn hàng
    public Order findById(String id) {
        return orders.get(id);
    }
}

//Chịu trách nhiệm gửi email xác nhận
class EmailService {
    public void sendEmail(Customer customer, String orderId) {
        System.out.println("Đã gửi email đến " + customer.email +
                ": Đơn hàng " + orderId + " đã được tạo");
    }
}

public class bai1 {
    public static void main(String[] args) {
        // Tạo sản phẩm
        Product sp1 = new Product("SP01", "Laptop", 15000000);
        Product sp2 = new Product("SP02", "Chuột", 300000);

        System.out.println("Đã thêm sản phẩm SP01, SP02");

        // Tạo khách hàng
        Customer customer = new Customer(
                "Nguyễn Văn A",
                "a@example.com",
                "Hà Nội"
        );

        System.out.println("Đã thêm khách hàng");

        // Tạo đơn hàng
        Order order = new Order("ORD001", customer);

        // thêm sản phẩm vào đơn
        order.addItem(sp1, 1);
        order.addItem(sp2, 2);

        System.out.println("Đơn hàng ORD001 được tạo");

        // Tính tổng tiền
        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);

        System.out.println("Tổng tiền: " + total);

        // Lưu đơn hàng
        OrderRepository repository = new OrderRepository();
        repository.save(order);

        // Gửi email xác nhận
        EmailService emailService = new EmailService();
        emailService.sendEmail(customer, order.orderId);
    }
}
