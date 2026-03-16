package bai4;

import java.util.*;

//Lưu thông tin đơn hàng
class Order {
    String id;

    public Order(String id) {
        this.id = id;
    }
}

//Định nghĩa các phương thức lưu và lấy đơn hàng
interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}

//Dùng để gửi thông báo
interface NotificationService {
    void send(String message, String recipient);
}

//Lưu đơn hàng vào file (giả lập bằng print)
class FileOrderRepository implements OrderRepository {
    private List<Order> orders = new ArrayList<>();
    @Override
    public void save(Order order) {
        orders.add(order);
        System.out.println("Lưu đơn hàng vào file: " + order.id);
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }
}

//Lưu đơn hàng vào database (giả lập)
class DatabaseOrderRepository implements OrderRepository {
    private List<Order> orders = new ArrayList<>();
    @Override
    public void save(Order order) {
        orders.add(order);
        System.out.println("Lưu đơn hàng vào database: " + order.id);
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }
}

//Gửi email thông báo
class EmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Gửi email: " + message);
    }
}

//Gửi SMS thông báo
class SMSNotification implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Gửi SMS: " + message);
    }
}

/*OrderServic High-level module Nhận dependency qua constructor (Dependency Injection)*/
class OrderService {
    private OrderRepository repository;
    private NotificationService notification;

    public OrderService(OrderRepository repository,
                        NotificationService notification) {
        this.repository = repository;
        this.notification = notification;
    }

    //Tạo đơn hàng
    public void createOrder(String orderId, String recipient) {
        Order order = new Order(orderId);
        // lưu đơn hàng
        repository.save(order);
        // gửi thông báo
        notification.send("Đơn hàng " + orderId + " đã được tạo", recipient);
    }
}


public class bai4 {
    public static void main(String[] args) {
        // Cấu hình 1: File + Email
        OrderRepository fileRepo = new FileOrderRepository();
        NotificationService email = new EmailService();
        OrderService service1 = new OrderService(fileRepo, email);
        service1.createOrder("ORD001", "user@example.com");
        System.out.println("------------------");

        // Cấu hình 2: Database + SMS
        OrderRepository dbRepo = new DatabaseOrderRepository();
        NotificationService sms = new SMSNotification();
        OrderService service2 = new OrderService(dbRepo, sms);
        service2.createOrder("ORD002", "0901234567");
    }
}
