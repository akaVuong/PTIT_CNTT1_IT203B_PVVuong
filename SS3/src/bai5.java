import java.util.*;

public class bai5 {
    record User(String username, String email, String status) {

    }
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alexander","alex@gmail.com","ACTIVE"),
                new User("bob","bob@yahoo.com","INACTIVE"),
                new User("charlotte","char@gmail.com","ACTIVE"),
                new User("Benjamin","ben@gmail.com","ACTIVE"),
                new User("tom","tom@gmail.com","ACTIVE")
        );

        users.stream()
                .sorted(Comparator.comparingInt((User u) -> u.username().length()).reversed())
                .limit(3)
                .forEach((User u) -> System.out.println(u.username()));
    }
}