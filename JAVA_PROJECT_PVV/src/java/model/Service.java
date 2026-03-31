package model;

public class Service {
    private int id;
    private String serviceName;
    private double price;

    public Service() {}
    public Service(int id, String serviceName, double price) {
        this.id = id;
        this.serviceName = serviceName;
        this.price = price;
    }
}