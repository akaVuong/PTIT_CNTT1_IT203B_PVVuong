package bai3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class UserProcessorTest {
    // Khai báo đối tượng sẽ dùng để test
    private UserProcessor processor;

    // Hàm này chạy trước mỗi test case
    @BeforeEach
    void setUp() {
        // Khởi tạo object UserProcessor
        processor = new UserProcessor();
    }

    @Test
    void shouldReturnValidEmailWhenFormatCorrect() {

        // Arrange: chuẩn bị dữ liệu đầu vào
        String input = "user@gmail.com";

        // Act: gọi phương thức cần kiểm tra
        String result = processor.processEmail(input);

        // Assert: kiểm tra kết quả mong đợi
        assertEquals("user@gmail.com", result);
    }

    @Test
    void shouldThrowExceptionWhenMissingAtSymbol() {

        // Arrange: email không có ký tự '@'
        String input = "usergmail.com";

        // Act + Assert: kiểm tra xem có ném exception không
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenDomainMissing() {

        // Arrange: email có '@' nhưng không có tên miền
        String input = "user@";

        // Act + Assert: mong đợi exception xảy ra
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail(input);
        });
    }

    @Test
    void shouldConvertEmailToLowercase() {

        // Arrange: email có chữ hoa
        String input = "Example@Gmail.com";

        // Act: xử lý email
        String result = processor.processEmail(input);

        // Assert: kiểm tra email đã được chuyển về chữ thường
        assertEquals("example@gmail.com", result);
    }
}