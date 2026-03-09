@FunctionalInterface
interface PasswordValidator {
    boolean isValid(String password);
}

public class bai2 {
    public static void main(String[] args) {
        PasswordValidator validator = password -> password.length() >= 8;
        System.out.println(validator.isValid("12345678"));
        System.out.println(validator.isValid("1234"));
    }
}