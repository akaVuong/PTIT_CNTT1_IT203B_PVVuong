import java.util.*;
import java.util.function.Supplier;

class User {
    private String username;

    public User() {
        this.username = "default";
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class bai4 {
    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Binh"),
                new User("Cuong")
        );
        // 1. User::getUsername
        users.stream()
                .map(User::getUsername)
                .forEach(System.out::println);
        // 2. System.out::println
        users.stream()
                .map(User::getUsername)
                .forEach(System.out::println);
        // 3. Constructor reference
        Supplier<User> supplier = User::new;
        User newUser = supplier.get();

        System.out.println(newUser.getUsername());
    }
}