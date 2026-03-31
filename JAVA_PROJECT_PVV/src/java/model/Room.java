package model;

public class Room {
    private int id;
    private String roomName;
    private int capacity;
    private String location;
    private String status;

    public Room() {}

    public Room(int id, String roomName, int capacity, String location, String status) {
        this.id = id;
        this.roomName = roomName;
        this.capacity = capacity;
        this.location = location;
        this.status = status;
    }

    // --- PHẢI CÓ ĐỦ CÁC HÀM NÀY ĐỂ ADMINMENU KHÔNG BÁO LỖI ---
    public int getId() { return id; }
    public String getRoomName() { return roomName; }
    public int getCapacity() { return capacity; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
}