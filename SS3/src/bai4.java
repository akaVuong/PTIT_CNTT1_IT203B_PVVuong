import java.util.*;
import java.util.stream.Collectors;
public class bai4 {
    record User(String username, String email, String status) {

    }
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@yahoo.com", "INACTIVE"),
                new User("alice", "alice2@gmail.com", "ACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE")
        );

        Collection<User> uniqueUsers = users.stream()
                .collect(Collectors.toMap(
                        User::username, // key
                        u -> u,  // value
                        (u1, u2) -> u1 // nếu trùng username thì giữ user đầu
                ))
                .values();

        uniqueUsers.forEach(System.out::println);
    }
}