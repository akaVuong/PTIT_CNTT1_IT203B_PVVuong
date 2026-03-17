package bai5;
import java.util.*;
// Đèn
class Light {
    public void off() {
        System.out.println("Đèn: Tắt");
    }
}

// Quạt (là Observer luôn)
class Fan implements Observer {
    private String speed = "Tắt";

    public void low() {
        speed = "Thấp";
        System.out.println("Quạt: Chạy tốc độ thấp");
    }

    public void high() {
        speed = "Mạnh";
        System.out.println("Quạt: Chạy tốc độ mạnh");
    }

    public void off() {
        speed = "Tắt";
        System.out.println("Quạt: Tắt");
    }

    @Override
    public void update(int temperature) {
        // tự điều chỉnh theo nhiệt độ
        if (temperature > 30) {
            high();
        }
    }
    public void status() {
        System.out.println("Quạt: " + speed);
    }
}

// Điều hòa (Observer)
class AirConditioner implements Observer {
    private int temperature = 25;

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }

    @Override
    public void update(int temp) {
        // có thể mở rộng logic
        if (temp > 30) {
            System.out.println("Điều hòa: Giữ mức 28 (có thể nâng cấp logic)");
        }
    }

    public void status() {
        System.out.println("Điều hòa: Nhiệt độ = " + temperature);
    }
}

//SUBJECT
class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    public void attach(Observer o) {
        observers.add(o);
    }

    public void detach(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;

        System.out.println("Cảm biến: Nhiệt độ = " + temperature);

        // khi nhiệt độ thay đổi -> notify
        notifyObservers();
    }
}

// COMMAND
// Macro command: Sleep Mode
class SleepModeCommand implements Command {
    private Light light;
    private Fan fan;
    private AirConditioner ac;

    public SleepModeCommand(Light light, Fan fan, AirConditioner ac) {
        this.light = light;
        this.fan = fan;
        this.ac = ac;
    }

    @Override
    public void execute() {
        System.out.println("SleepMode: Tắt đèn");
        light.off();

        System.out.println("SleepMode: Điều hòa set 28°C");
        ac.setTemperature(28);

        System.out.println("SleepMode: Quạt tốc độ thấp");
        fan.low();
    }
}
