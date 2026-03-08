public class bai3 {
    private int age;
    // Phương thức setAge
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Tuổi không thể âm!");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    public static void main(String[] args) {
        bai3 p = new bai3();   // sửa Person -> bai3
        try {
            p.setAge(-5); // thử nhập tuổi âm
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        System.out.println("Chương trình vẫn tiếp tục chạy.");
    }
}