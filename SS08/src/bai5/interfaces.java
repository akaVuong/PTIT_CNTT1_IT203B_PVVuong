package bai5;
// COMMAND
interface Command {
    void execute(); // thực thi lệnh
}

//OBSERVER
interface Observer {
    void update(int temperature); // nhận nhiệt độ
}

// SUBJECT
interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}
