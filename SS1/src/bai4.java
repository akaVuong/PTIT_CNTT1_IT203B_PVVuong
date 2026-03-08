import java.io.IOException;
public class bai4 {
    // Method C
    public static void saveToFile() throws IOException {
        System.out.println("Đang lưu dữ liệu vào file...");
        // Giả lập lỗi khi ghi file
        throw new IOException("Lỗi khi ghi file!");
    }
    // Method B
    public static void processUserData() throws IOException {
        System.out.println("Đang xử lý dữ liệu người dùng...");

        // Gọi method C
        saveToFile();
    }
    // Method A (điểm chốt chặn cuối cùng)
    public static void main(String[] args) {
        try {
            processUserData();
        } catch (IOException e) {
            System.out.println("Đã bắt lỗi tại main: " + e.getMessage());
        }
        System.out.println("Chương trình vẫn tiếp tục chạy sau khi xử lý lỗi.");
    }
}