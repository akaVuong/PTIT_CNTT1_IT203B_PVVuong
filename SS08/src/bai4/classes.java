package bai4;
import java.util.ArrayList;
import java.util.List;

//SUBJECT
class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;
    // đăng ký thiết bị
    public void attach(Observer o) {
        observers.add(o);
    }

    // hủy đăng ký
    public void detach(Observer o) {
        observers.remove(o);
    }

    // thông báo cho tất cả observer
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    // thay đổi nhiệt độ
    public void setTemperature(int temperature) {
        this.temperature = temperature;

        // in ra cảm biến nhận giá trị mới
        System.out.println("Cảm biến: Nhiệt độ = " + temperature);

        // khi thay đổi -> tự động notify
        notifyObservers();
    }
}

// OBSERVERS
// Quạt
class Fan implements Observer {

    @Override
    public void update(int temperature) {
        // logic theo đề bài
        if (temperature < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        } else if (temperature <= 25) {
            System.out.println("Quạt: Chạy tốc độ trung bình");
        } else {
            System.out.println("Quạt: Chạy tốc độ mạnh");
        }
    }
}

// Máy tạo ẩm
class Humidifier implements Observer {

    @Override
    public void update(int temperature) {
        // chỉ in thông báo
        System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm theo nhiệt độ " + temperature);
    }
}
