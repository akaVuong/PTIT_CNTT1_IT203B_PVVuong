import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class bai6 {
    // Custom Exception cho nghiệp vụ tuổi
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String msg) {
            super(msg);
        }
    }
    // Lớp User
    static class User {
        private String name;
        private int age;
        public User(String name) {
            this.name = name;
        }

        public void setAge(int age) throws InvalidAgeException {
            if (age < 0) {
                throw new InvalidAgeException("Tuổi không hợp lệ: Tuổi không thể âm!");
            }
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    // Giả lập ghi log lỗi
    public static void logError(String message, Exception e) {
        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[ERROR] " + time + " - " + message);
        System.out.println("Chi tiết: " + e.getMessage());
    }

    // Giả lập thao tác với file (Checked Exception)
    public static void saveUserToFile(User user) throws IOException {
        System.out.println("Đang lưu dữ liệu người dùng vào file...");
        throw new IOException("Không thể ghi file (giả lập lỗi IO).");
    }

    public static void main(String[] args) {
        User user = new User("Nam");
        try {
            // Lỗi nghiệp vụ (custom exception)
            user.setAge(-5);
            // Phòng ngừa NullPointerException bằng if
            if (user.getName() != null) {
                System.out.println("Tên người dùng: " + user.getName());
            } else {
                System.out.println("Tên người dùng chưa được thiết lập.");
            }
            // Lỗi ngoại cảnh (IO)
            saveUserToFile(user);
        } catch (InvalidAgeException e) {
            logError("Lỗi nghiệp vụ khi đặt tuổi.", e);

        } catch (IOException e) {
            logError("Lỗi khi lưu dữ liệu ra file.", e);
        }
        System.out.println("Chương trình vẫn tiếp tục chạy bình thường.");
    }
}