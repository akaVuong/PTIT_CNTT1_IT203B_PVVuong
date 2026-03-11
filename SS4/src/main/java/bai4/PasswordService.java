package bai4;

public class PasswordService {
    // Phương thức đánh giá độ mạnh của mật khẩu
    public String evaluatePasswordStrength(String password) {
        // Kiểm tra mật khẩu null hoặc độ dài nhỏ hơn 8 ký tự
        // Nếu đúng thì trả về "Yếu"
        if (password == null || password.length() < 8) {
            return "Yếu";
        }

        // Kiểm tra các điều kiện của mật khẩu
        boolean hasUpper = password.matches(".*[A-Z].*"); // có chữ hoa
        boolean hasLower = password.matches(".*[a-z].*"); // có chữ thường
        boolean hasDigit = password.matches(".*[0-9].*"); // có số
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*"); // có ký tự đặc biệt

        // Đếm số điều kiện mà mật khẩu thỏa mãn
        int count = 0;
        if (hasUpper) count++;   // nếu có chữ hoa thì tăng count
        if (hasLower) count++;   // nếu có chữ thường thì tăng count
        if (hasDigit) count++;   // nếu có số thì tăng count
        if (hasSpecial) count++; // nếu có ký tự đặc biệt thì tăng count

        // Nếu đủ cả 4 điều kiện -> mật khẩu "Mạnh"
        if (count == 4) {
            return "Mạnh";
        }

        // Nếu đủ 3 điều kiện -> mật khẩu "Trung bình"
        if (count == 3) {
            return "Trung bình";
        }

        // Các trường hợp còn lại -> mật khẩu "Yếu"
        return "Yếu";
    }
}