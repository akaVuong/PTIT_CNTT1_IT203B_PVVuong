package bai3;

@FunctionalInterface
public interface Authenticatable {

    // 1. Phương thức trừu tượng
    String getPassword();

    // 2. Default method kiểm tra xác thực
    default boolean isAuthenticated() {
        return getPassword() != null && !getPassword().isEmpty();
    }

    // 3. Static method mô phỏng mã hóa mật khẩu
    static String encrypt(String rawPassword) {
        return "ENC_" + rawPassword;
    }
}
