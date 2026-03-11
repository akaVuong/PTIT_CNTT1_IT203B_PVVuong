package bai2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Test
    void testAge18_valid() {

        // Arrange
        UserService service = new UserService();
        int age = 18;

        // Act
        boolean result = service.checkRegistrationAge(age);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void testAge17_invalid() {

        // Arrange
        UserService service = new UserService();
        int age = 17;

        // Act
        boolean result = service.checkRegistrationAge(age);

        // Assert
        assertEquals(false, result);
    }

    @Test
    void testNegativeAge_exception() {

        // Arrange
        UserService service = new UserService();
        int age = -1;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.checkRegistrationAge(age);
        });
    }
}