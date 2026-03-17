package bai1;

// Interface chung cho thiết bị
interface Device {
    void turnOn();
    void turnOff();
}

// Abstract Factory (Factory Method)
abstract class DeviceFactory {
    // phương thức tạo thiết bị (class con sẽ override)
    abstract Device createDevice();
}
