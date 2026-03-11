package bai3;

public class UserProcessor {
    public String processEmail(String email) {
        // Kiểm tra email null hoặc không chứa ký tự '@'
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Tách email thành 2 phần: trước @ và sau @
        String[] parts = email.split("@");
        // Nếu email không có phần domain sau @ thì không hợp lệ
        if (parts.length != 2 || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Chuẩn hóa email: chuyển tất cả về chữ thường
        return email.toLowerCase();
    }
}
