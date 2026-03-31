package model;

public class Equipment {
    private int id;
    private String name;
    private int totalQty;
    private int availableQty;
    private String status;

    public Equipment() {}

    public Equipment(int id, String name, int totalQty, int availableQty, String status) {
        this.id = id;
        this.name = name;
        this.totalQty = totalQty;
        this.availableQty = availableQty;
        this.status = status;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getTotalQty() { return totalQty; }
    public int getAvailableQty() { return availableQty; }
    public String getStatus() { return status; }

    public void setTotalQty(int totalQty) { this.totalQty = totalQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }
}