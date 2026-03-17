package bai4;
// Observer (các thiết bị nhận thông báo)
interface Observer {
    // nhận nhiệt độ từ Subject
    void update(int temperature);
}
// Subject (cảm biến nhiệt độ)
interface Subject {
    void attach(Observer o);   // đăng ký observer
    void detach(Observer o);   // hủy đăng ký
    void notifyObservers();    // gửi thông báo
}
