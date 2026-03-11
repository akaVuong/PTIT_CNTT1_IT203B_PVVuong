package bai4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest {

    // Tạo đối tượng PasswordService để sử dụng trong các test
    PasswordService service = new PasswordService();

    @Test
    void testStrongPassword() {

        // Arrange: mật khẩu có chữ hoa, chữ thường, số và ký tự đặc biệt
        String input = "Abc123!@";

        // Act: gọi phương thức đánh giá mật khẩu
        String result = service.evaluatePasswordStrength(input);

        // Assert: kiểm tra kết quả mong đợi là "Mạnh"
        assertEquals("Mạnh", result);
    }

    @Test
    void testMissingUppercase() {

        // Arrange: mật khẩu thiếu chữ hoa
        String input = "abc123!@";

        // Act
        String result = service.evaluatePasswordStrength(input);

        // Assert: kết quả mong đợi là "Trung bình"
        assertEquals("Trung bình", result);
    }

    @Test
    void testMissingLowercase() {

        // Arrange: mật khẩu thiếu chữ thường
        String input = "ABC123!@";

        // Act
        String result = service.evaluatePasswordStrength(input);

        // Assert
        assertEquals("Trung bình", result);
    }

    @Test
    void testMissingNumber() {

        // Arrange: mật khẩu thiếu số
        String input = "Abcdef!@";

        // Act
        String result = service.evaluatePasswordStrength(input);

        // Assert
        assertEquals("Trung bình", result);
    }

    @Test
    void testMissingSpecialCharacter() {

        // Arrange: mật khẩu thiếu ký tự đặc biệt
        String input = "Abc12345";

        // Act
        String result = service.evaluatePasswordStrength(input);

        // Assert
        assertEquals("Trung bình", result);
    }

    @Test
    void testShortPassword() {

        // Arrange: mật khẩu quá ngắn (< 8 ký tự)
        String input = "Ab1!";

        // Act
        String result = service.evaluatePasswordStrength(input);

        // Assert: kết quả phải là "Yếu"
        assertEquals("Yếu", result);
    }

    @Test
    void testOnlyLowercase() {

        // Arrange: mật khẩu chỉ có chữ thường
        String input = "password";

        // Act
        String result = service.evaluatePasswordStrength(input);

        // Assert
        assertEquals("Yếu", result);
    }

    @Test
    void testUppercaseAndNumberOnly() {

        // Arrange: mật khẩu chỉ có chữ hoa và số
        String input = "ABC12345";

        // Act
        String result = service.evaluatePasswordStrength(input);

        // Assert: theo bảng test case -> "Yếu"
        assertEquals("Yếu", result);
    }
}