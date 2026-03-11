package bai6;

import java.time.LocalDate;
import java.util.List;

// Class chứa logic cập nhật hồ sơ
public class UserProfileService {

    // Phương thức cập nhật hồ sơ user
    public User updateProfile(User existingUser, UserProfile newProfile, List<User> allUsers) {

        // Kiểm tra ngày sinh có nằm trong tương lai không
        if (newProfile.getBirthDate().isAfter(LocalDate.now())) {
            return null; // Không cho cập nhật
        }

        // Kiểm tra email có trùng với user khác không
        for (User u : allUsers) {

            // Nếu email trùng nhưng không phải user hiện tại
            if (u.getEmail().equals(newProfile.getEmail())
                    && !u.getEmail().equals(existingUser.getEmail())) {

                return null; // Không cho cập nhật
            }
        }

        // Nếu hợp lệ -> cập nhật thông tin
        existingUser.setEmail(newProfile.getEmail());
        existingUser.setBirthDate(newProfile.getBirthDate());

        // Trả về user đã cập nhật
        return existingUser;
    }
}


// Class User đơn giản
class User {

    private String email;
    private LocalDate birthDate;

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}


// Class chứa dữ liệu profile mới
class UserProfile {

    private String email;
    private LocalDate birthDate;

    public UserProfile(String email, LocalDate birthDate) {
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
