package services;

import dao.UserDAO;
import models.User;
import java.sql.Connection;
import java.util.List;

public class UserService {
    private UserDAO userDAO;
    public UserService(Connection conn) {
        userDAO = new UserDAO(conn);
    }
    // lấy danh sách user
    public List<User> getAllUsers() {
        // chỉ gọi xuống DAO để lấy dữ liệu
        return userDAO.findAll();
    }
    // thêm user
    public boolean addUser(User user) {
        // kiểm tra dữ liệu trước khi cho xuống DB
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            System.out.println("Tên không được để trống");
            return false;
        }
        // hợp lệ thì gọi DAO để insert
        return userDAO.insert(user);
    }
}