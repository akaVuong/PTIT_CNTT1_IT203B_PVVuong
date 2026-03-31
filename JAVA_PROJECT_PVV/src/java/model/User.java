package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private int departmentId; // <-- THÊM DÒNG NÀY
    private String phone;

    public User() {}

    public User(int id, String username, String password, String fullName, String role, int departmentId, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.departmentId = departmentId; // <-- GÁN GIÁ TRỊ VÀO ĐÂY
        this.phone = phone;
    }

    // --- Getter & Setter cho DepartmentId ---
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    // Đảm bảo các Getter khác vẫn đầy đủ...
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public String getPhone() { return phone; }

    public void setPassword(String password) { this.password = password; }
}