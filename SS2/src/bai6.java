@FunctionalInterface
interface UserProcessor {
    String process(Users u); //phương thức xử lý một đối tượng Users và trả về String
}

class UserUtils {
    // phương thức tĩnh dùng để chuyển username của Users thành chữ in hoa
    public static String convertToUpperCase(Users u) {
        return u.getUsername().toUpperCase();
    }
}

// Lớp Users dùng để lưu thông tin người dùng
class Users {
    private String username;
    public Users(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class bai6 {
    public static void main(String[] args) {
        Users user = new Users("binh");
        //dùng Method Reference tới phương thức convertToUpperCase
        UserProcessor processor = UserUtils::convertToUpperCase;
        // gọi process để xử lý user và in kết quả
        System.out.println(processor.process(user));
    }
}