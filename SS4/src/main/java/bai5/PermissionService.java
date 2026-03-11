package bai5;

// Enum định nghĩa vai trò của user
enum Role {
    ADMIN,
    MODERATOR,
    USER
}

// Enum định nghĩa các hành động
enum Action {
    DELETE_USER,
    LOCK_USER,
    VIEW_PROFILE
}

// Class mô phỏng user
class User {

    // Thuộc tính role
    private Role role;

    // Constructor khởi tạo role
    public User(Role role) {
        this.role = role;
    }

    // Getter lấy role
    public Role getRole() {
        return role;
    }
}

// Service kiểm tra quyền
public class PermissionService {

    // Kiểm tra user có được phép thực hiện action hay không
    public boolean canPerformAction(User user, Action action) {

        // Nếu là ADMIN -> có toàn quyền
        if (user.getRole() == Role.ADMIN) {
            return true;
        }

        // Nếu là MODERATOR
        if (user.getRole() == Role.MODERATOR) {

            // MODERATOR không được DELETE_USER
            if (action == Action.DELETE_USER) {
                return false;
            }

            // Nhưng được LOCK_USER và VIEW_PROFILE
            return true;
        }

        // Nếu là USER thường
        if (user.getRole() == Role.USER) {

            // USER chỉ được VIEW_PROFILE
            if (action == Action.VIEW_PROFILE) {
                return true;
            }

            return false;
        }

        return false;
    }
}
