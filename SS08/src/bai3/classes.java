package bai3;
import java.util.Stack;
// Đèn
class Light {
    public void on() {
        System.out.println("Đèn: Bật");
    }

    public void off() {
        System.out.println("Đèn: Tắt");
    }
}

// Quạt
class Fan {
    public void on() {
        System.out.println("Quạt: Bật");
    }

    public void off() {
        System.out.println("Quạt: Tắt");
    }
}

// Điều hòa
class AirConditioner {
    private int temperature = 25; // nhiệt độ mặc định

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }

    public int getTemperature() {
        return temperature;
    }
}

//COMMAND IMPLEMENT
// Bật đèn
class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) {
        this.light = light;
    }
    public void execute() {
        light.on(); // gọi receiver
    }
    public void undo() {
        light.off(); // undo = tắt đèn
    }
}

// Tắt đèn
class LightOffCommand implements Command {
    private Light light;
    public LightOffCommand(Light light) {
        this.light = light;
    }
    public void execute() {
        light.off();
    }
    public void undo() {
        light.on(); // undo = bật lại
    }
}

// Bật quạt
class FanOnCommand implements Command {
    private Fan fan;
    public FanOnCommand(Fan fan) {
        this.fan = fan;
    }
    public void execute() {
        fan.on();
    }
    public void undo() {
        fan.off();
    }
}

// Tắt quạt
class FanOffCommand implements Command {
    private Fan fan;
    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }
    public void execute() {
        fan.off();
    }
    public void undo() {
        fan.on();
    }
}

// Set nhiệt độ điều hòa (có lưu trạng thái cũ để undo)
class ACSetTemperatureCommand implements Command {
    private AirConditioner ac;
    private int newTemp;
    private int prevTemp; // lưu nhiệt độ cũ
    public ACSetTemperatureCommand(AirConditioner ac, int newTemp) {
        this.ac = ac;
        this.newTemp = newTemp;
    }
    public void execute() {
        // lưu trạng thái trước khi thay đổi
        prevTemp = ac.getTemperature();

        ac.setTemperature(newTemp);
    }
    public void undo() {
        // quay lại nhiệt độ cũ
        ac.setTemperature(prevTemp);
        System.out.println("Undo: Điều hòa quay lại " + prevTemp);
    }
}

// UNDO MANAGER
class UndoManager {
    // stack lưu lịch sử command
    private Stack<Command> history = new Stack<>();
    // lưu command sau khi execute
    public void push(Command cmd) {
        history.push(cmd);
    }
    // thực hiện undo
    public void undo() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            cmd.undo(); // gọi undo của command
        } else {
            System.out.println("Không có lệnh để undo!");
        }
    }
}

//REMOTE CONTROL
class RemoteControl {
    private Command[] slots; // mỗi slot chứa 1 command
    private UndoManager undoManager;

    public RemoteControl(int size, UndoManager undoManager) {
        slots = new Command[size];
        this.undoManager = undoManager;
    }
    // gán command cho nút
    public void setCommand(int index, Command command) {
        slots[index] = command;
        System.out.println("Đã gán command cho nút " + (index + 1));
    }
    // nhấn nút
    public void pressButton(int index) {
        if (slots[index] != null) {
            slots[index].execute();          // chạy lệnh
            undoManager.push(slots[index]);  // lưu vào history
        } else {
            System.out.println("Nút chưa được gán lệnh!");
        }
    }
}
