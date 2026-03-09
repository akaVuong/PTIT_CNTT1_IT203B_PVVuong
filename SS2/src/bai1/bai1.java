package bai1;

import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', role='" + role + "'}";
    }
}

public class bai1 {
    public static void main(String[] args) {
        User user = new User("admin01", "ADMIN");
        // 1. Predicate: kiểm tra User có phải Admin không
        Predicate<User> isAdmin = u -> u.getRole().equals("ADMIN");
        System.out.println("Is Admin: " + isAdmin.test(user));

        // 2. Function: chuyển User thành String (username)
        Function<User, String> getUsername = u -> u.getUsername();
        System.out.println("Username: " + getUsername.apply(user));

        // 3. Consumer: in thông tin User ra console
        Consumer<User> printUser = u -> System.out.println("User info: " + u);
        printUser.accept(user);

        // 4. Supplier: tạo User mặc định
        Supplier<User> defaultUser = () -> new User("guest", "USER");
        User newUser = defaultUser.get();
        System.out.println("Default User: " + newUser);
    }
}
