package models;

public class Product {
    private int productId;
    private String productName;
    private int stock;
    private double price;

    public Product(int productId, String productName, int stock, double price) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
    }

    public Product(String productName, int stock, double price) {
        this.productName = productName;
        this.stock = stock;
        this.price = price;
    }

    // Getters & Setters
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-10d %-10.2f",
                productId,
                productName,
                stock,
                price);
    }
}
