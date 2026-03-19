package bai2;

//OLD CLASS (không sửa được)
class OldThermometer {
    // giả lập trả về nhiệt độ theo Fahrenheit
    public int getTemperatureFahrenheit() {
        return 78; // ví dụ 78°F
    }
}

// ADAPTER
class ThermometerAdapter implements TemperatureSensor {
    private OldThermometer oldThermometer;

    // truyền object cũ vào adapter
    public ThermometerAdapter(OldThermometer oldThermometer) {
        this.oldThermometer = oldThermometer;
    }

    @Override
    public double getTemperatureCelsius() {
        // lấy nhiệt độ F từ cảm biến cũ
        int f = oldThermometer.getTemperatureFahrenheit();

        // chuyển đổi F -> C
        double c = (f - 32) * 5.0 / 9;

        return c;
    }
}

// THIẾT BỊ
class Light {
    public void off() {
        System.out.println("Đèn: Tắt");
    }
}

class Fan {
    public void off() {
        System.out.println("Quạt: Tắt");
    }

    public void lowSpeed() {
        System.out.println("Quạt: Chạy tốc độ thấp");
    }
}

class `AirConditioner {
    public void off() {
        System.out.println("Điều hòa: Tắt");
    }

    public void setTemperature(int temp) {
        System.out.println("Điều hòa: Set " + temp + "°C");
    }
}

//  FACADE
class SmartHomeFacade {
    private Light light;
    private Fan fan;
    private AirConditioner ac;
    private TemperatureSensor sensor;

    // constructor nhận tất cả subsystem
    public SmartHomeFacade(Light light, Fan fan, AirConditioner ac, TemperatureSensor sensor) {
        this.light = light;
        this.fan = fan;
        this.ac = ac;
        this.sensor = sensor;
    }

    // chế độ rời nhà
    public void leaveHome() {
        System.out.println("FACADE: Tắt đèn");
        light.off();

        System.out.println("FACADE: Tắt quạt");
        fan.off();

        System.out.println("FACADE: Tắt điều hòa");
        ac.off();
    }

    // chế độ ngủ
    public void sleepMode() {
        System.out.println("FACADE: Tắt đèn");
        light.off();

        System.out.println("FACADE: Điều hòa set 28°C");
        ac.setTemperature(28);

        System.out.println("FACADE: Quạt chạy tốc độ thấp");
        fan.lowSpeed();
    }

    // lấy nhiệt độ từ cảm biến (dùng adapter)
    public double getCurrentTemperature() {
        return sensor.getTemperatureCelsius();
    }
}
