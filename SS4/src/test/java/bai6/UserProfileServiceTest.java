package bai6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileServiceTest {

    // Khai báo service cần test
    private UserProfileService service;

    // Danh sách user giả lập
    private List<User> allUsers;

    // Chạy trước mỗi test
    @BeforeEach
    void setUp() {

        // Khởi tạo service
        service = new UserProfileService();

        // Tạo danh sách user
        allUsers = new ArrayList<>();

        // Thêm 1 user mẫu
        allUsers.add(new User("user1@gmail.com"));
    }

    // Chạy sau mỗi test
    @AfterEach
    void tearDown() {
        // Xóa dữ liệu test
        allUsers.clear();
    }

    // TC1: Email hợp lệ + ngày sinh hợp lệ
    @Test
    void shouldUpdateProfileSuccessfully() {

        // Arrange
        User existingUser = new User("user1@gmail.com");

        UserProfile newProfile =
                new UserProfile("new@gmail.com", LocalDate.of(2000,5,10));

        // Act
        User result = service.updateProfile(existingUser, newProfile, allUsers);

        // Assert
        assertNotNull(result);
    }

    // TC2: Ngày sinh trong tương lai
    @Test
    void shouldReturnNullWhenBirthDateFuture() {

        // Arrange
        User existingUser = new User("user1@gmail.com");

        UserProfile newProfile =
                new UserProfile("new@gmail.com", LocalDate.now().plusDays(1));

        // Act
        User result = service.updateProfile(existingUser, newProfile, allUsers);

        // Assert
        assertNull(result);
    }

    // TC3: Email trùng user khác
    @Test
    void shouldReturnNullWhenEmailDuplicated() {

        // Arrange
        allUsers.add(new User("duplicate@gmail.com"));

        User existingUser = new User("user1@gmail.com");

        UserProfile newProfile =
                new UserProfile("duplicate@gmail.com", LocalDate.of(2000,1,1));

        // Act
        User result = service.updateProfile(existingUser, newProfile, allUsers);

        // Assert
        assertNull(result);
    }

    // TC4: Email giữ nguyên
    @Test
    void shouldUpdateWhenEmailNotChanged() {

        // Arrange
        User existingUser = new User("user1@gmail.com");

        UserProfile newProfile =
                new UserProfile("user1@gmail.com", LocalDate.of(1999,10,10));

        // Act
        User result = service.updateProfile(existingUser, newProfile, allUsers);

        // Assert
        assertNotNull(result);
    }

    // TC5: Danh sách user rỗng
    @Test
    void shouldUpdateWhenUserListEmpty() {

        // Arrange
        allUsers.clear();

        User existingUser = new User("user1@gmail.com");

        UserProfile newProfile =
                new UserProfile("new@gmail.com", LocalDate.of(2000,1,1));

        // Act
        User result = service.updateProfile(existingUser, newProfile, allUsers);

        // Assert
        assertNotNull(result);
    }

    // TC6: Email trùng + ngày sinh tương lai
    @Test
    void shouldReturnNullWhenMultipleErrors() {

        // Arrange
        allUsers.add(new User("duplicate@gmail.com"));

        User existingUser = new User("user1@gmail.com");

        UserProfile newProfile =
                new UserProfile("duplicate@gmail.com", LocalDate.now().plusDays(3));

        // Act
        User result = service.updateProfile(existingUser, newProfile, allUsers);

        // Assert
        assertNull(result);
    }
}