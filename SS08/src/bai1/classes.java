package bai1;
// ================== Singleton ==================
class HardwareConnection {
    // lưu instance duy nhất
    private static HardwareConnection instance;
    // constructor private -> không cho new từ bên ngoài
    private HardwareConnection() {}

    // lấy instance duy nhất
    public static HardwareConnection getInstance() {
        // nếu chưa có thì tạo mới
        if (instance == null) {
            instance = new HardwareConnection();
            System.out.println("HardwareConnection: Đã kết nối phần cứng (Chỉ hiện 1 lần duy nhất)");
        }
        // trả về instance (cũ hoặc mới)
        return instance;
    }
}


// Đèn
class Light implements Device {
    @Override
    public void turnOn() {
        System.out.println("Đèn: Bật sáng.");
    }

    @Override
    public void turnOff() {
        System.out.println("Đèn: Tắt.");
    }
}

// Quạt
class Fan implements Device {
    @Override
    public void turnOn() {
        System.out.println("Quạt: Quay.");
    }

    @Override
    public void turnOff() {
        System.out.println("Quạt: Dừng.");
    }
}

// Điều hòa
class AirConditioner implements Device {
    @Override
    public void turnOn() {
        System.out.println("Điều hòa: Bật.");
    }

    @Override
    public void turnOff() {
        System.out.println("Điều hòa: Tắt.");
    }
}


// Factory tạo Đèn
class LightFactory extends DeviceFactory {
    @Override
    public Device createDevice() {
        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new Light(); // tạo object Light
    }
}

// Factory tạo Quạt
class FanFactory extends DeviceFactory {
    @Override
    public Device createDevice() {
        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new Fan();
    }
}

// Factory tạo Điều hòa
class AirConditionerFactory extends DeviceFactory {
    @Override
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new AirConditioner();
    }
}
