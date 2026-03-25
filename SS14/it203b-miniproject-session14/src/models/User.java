package models;

public class User {
    private int userId;
    private String fullName;
    private String address;

    public User(int userId, String fullName, String address) {
        this.userId = userId;
        this.fullName = fullName;
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
