package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

public class AuthService {
    private UserDAO userDAO = new UserDAO();

    public User login(String user, String pass) {
        User u = userDAO.getUserByUsername(user);
        if (u != null && PasswordUtil.checkPassword(pass, u.getPassword())) {
            return u;
        }
        return null;
    }

    // HÀM QUAN TRỌNG: Xử lý đăng ký chung cho tất cả các Role kèm Validate
    public String registerNewUser(String user, String pass, String name, int deptId, String phone, String role) {
        // 1. Validate: Không để trống và độ dài mật khẩu
        if (user == null || user.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            return " Thất bại: Tài khoản/Mật khẩu không được để trống!";
        }
        if (pass.length() < 6) {
            return " Thất bại: Mật khẩu phải có tối thiểu 6 ký tự!";
        }

        // 2. Validate: Check trùng Username qua DAO
        if (userDAO.isUsernameExists(user)) {
            return " Thất bại: Tên đăng nhập '" + user + "' đã tồn tại!";
        }

        // 3. Thực hiện Hash và Lưu
        String hashed = PasswordUtil.hashPassword(pass);
        User newUser = new User(0, user, hashed, name, role, deptId, phone);

        boolean success = userDAO.register(newUser);
        return success ? " Thành công: Đã tạo tài khoản " + role : " Thất bại: Lỗi hệ thống!";
    }
}