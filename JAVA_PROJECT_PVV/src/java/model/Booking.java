package model;
import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int userId;
    private int roomId;
    private int equipmentId; // MỚI: Dùng để lưu ID thiết bị mượn kèm
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;

    // Constructor cập nhật thêm tham số equipmentId
    public Booking(int id, int userId, int roomId, int equipmentId, LocalDateTime startTime, LocalDateTime endTime, String purpose, String status) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.equipmentId = equipmentId; //
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.status = status;
    }

    // --- GETTERS ---
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getRoomId() { return roomId; }
    public int getEquipmentId() { return equipmentId; } //
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getPurpose() { return purpose; }
    public String getStatus() { return status; }

    // --- SETTERS (Cần thiết để gán ID thiết bị sau khi khởi tạo) ---
    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; } //
    public void setStatus(String status) { this.status = status; }
}