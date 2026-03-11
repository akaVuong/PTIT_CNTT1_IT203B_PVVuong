package bai1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UserValidatorTest {
    @Test
    void TC01_validUsername() {
        // Arrange
        UserValidator validator = new UserValidator();
        String input = "user123";
        // Act
        boolean result = validator.isValidUsername(input);
        // Assert
        assertTrue(result);
    }

    @Test
    void TC02_usernameTooShort() {
        // Arrange
        UserValidator validator = new UserValidator();
        String input = "abc";
        // Act
        boolean result = validator.isValidUsername(input);

        // Assert
        assertFalse(result);
    }

    @Test
    void TC03_usernameContainsSpace() {
        // Arrange
        UserValidator validator = new UserValidator();
        String input = "user name";
        // Act
        boolean result = validator.isValidUsername(input);
        // Assert
        assertFalse(result);
    }
}