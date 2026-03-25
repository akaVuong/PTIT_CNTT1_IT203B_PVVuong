package dao;

import models.Product;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Thêm sản phẩm
    public void addProduct(Product product) {
        String sql = "INSERT INTO products(product_name, stock, price) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getStock());
            ps.setDouble(3, product.getPrice());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println(" Thêm sản phẩm thành công!");
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    // Lấy danh sách sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_name, stock, price FROM products";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("stock"),
                        rs.getDouble("price")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }
        return products;
    }
    // Cập nhật tồn kho sản phẩm
    public boolean updateStock(int productId, int newQuantity) {
        String sql = "UPDATE products SET stock = ? WHERE product_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(" Lỗi khi cập nhật tồn kho: " + e.getMessage());
            return false;
        }
    }
}
