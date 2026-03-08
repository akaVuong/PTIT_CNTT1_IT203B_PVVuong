public class bai5 {
    // Custom Exception
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String msg) {
            super(msg);
        }
    }
    // Lớp User
    static class User {
        private int age;

        public void setAge(int age) throws InvalidAgeException {
            if (age < 0) {
                throw new InvalidAgeException("Tuổi không hợp lệ: Tuổi không thể âm!");
            }
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }
    public static void main(String[] args) {
        User user = new User();
        try {
            user.setAge(-10); // thử nhập tuổi không hợp lệ
        } catch (InvalidAgeException e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace(); // in stack trace để thấy tên InvalidAgeException
        }
        System.out.println("Chương trình vẫn tiếp tục chạy.");
    }
}