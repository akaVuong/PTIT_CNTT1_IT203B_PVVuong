package bai5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

class PermissionServiceTest {
    PermissionService service = new PermissionService();
    User admin;
    User moderator;
    User user;
    // TEST ROLE ADMIN (Top-Down)
    @Test
    void adminCanDeleteUser() {

        // Arrange: tạo user có role ADMIN
        admin = new User(Role.ADMIN);

        // Act: kiểm tra quyền DELETE_USER
        boolean result = service.canPerformAction(admin, Action.DELETE_USER);

        // Assert: ADMIN phải được phép
        assertTrue(result);
    }

    @Test
    void adminCanLockUser() {

        // Arrange
        admin = new User(Role.ADMIN);

        // Act
        boolean result = service.canPerformAction(admin, Action.LOCK_USER);

        // Assert
        assertTrue(result);
    }

    @Test
    void adminCanViewProfile() {

        // Arrange
        admin = new User(Role.ADMIN);

        // Act
        boolean result = service.canPerformAction(admin, Action.VIEW_PROFILE);

        // Assert
        assertTrue(result);
    }

    // ========================
    // TEST ROLE MODERATOR
    // ========================

    @Test
    void moderatorCannotDeleteUser() {

        // Arrange
        moderator = new User(Role.MODERATOR);

        // Act
        boolean result = service.canPerformAction(moderator, Action.DELETE_USER);

        // Assert
        assertFalse(result);
    }

    @Test
    void moderatorCanLockUser() {

        // Arrange
        moderator = new User(Role.MODERATOR);

        // Act
        boolean result = service.canPerformAction(moderator, Action.LOCK_USER);

        // Assert
        assertTrue(result);
    }

    @Test
    void moderatorCanViewProfile() {

        // Arrange
        moderator = new User(Role.MODERATOR);

        // Act
        boolean result = service.canPerformAction(moderator, Action.VIEW_PROFILE);

        // Assert
        assertTrue(result);
    }

    // ========================
    // TEST ROLE USER
    // ========================

    @Test
    void userCannotDeleteUser() {

        // Arrange
        user = new User(Role.USER);

        // Act
        boolean result = service.canPerformAction(user, Action.DELETE_USER);

        // Assert
        assertFalse(result);
    }

    @Test
    void userCannotLockUser() {

        // Arrange
        user = new User(Role.USER);

        // Act
        boolean result = service.canPerformAction(user, Action.LOCK_USER);

        // Assert
        assertFalse(result);
    }

    @Test
    void userCanViewProfile() {

        // Arrange
        user = new User(Role.USER);

        // Act
        boolean result = service.canPerformAction(user, Action.VIEW_PROFILE);

        // Assert
        assertTrue(result);
    }

    // ========================
    // Dọn dẹp sau mỗi test
    // ========================

    @AfterEach
    void cleanUp() {

        // Reset lại các đối tượng sau mỗi test
        admin = null;
        moderator = null;
        user = null;
    }
}