import java.util.*;

public class bai3 {
    // Record User
    record User(String username, String email, String status) {
    }
    // Repository
    static class UserRepository {
        private List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@yahoo.com", "INACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE")
        );

        public Optional<User> findUserByUsername(String username) {
            return users.stream()
                    .filter(user -> user.username().equals(username))
                    .findFirst();
        }
    }

    public static void main(String[] args) {
        UserRepository repo = new UserRepository();
        repo.findUserByUsername("alice")
                .ifPresentOrElse(
                        user -> System.out.println("Welcome " + user.username()),
                        () -> System.out.println("Guest login")
                );
    }
}