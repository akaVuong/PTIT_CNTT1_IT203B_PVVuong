package services;

import dao.ProductDAO;
import models.Product;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    public List<Product> showProducts() {
        return productDAO.getAllProducts();
    }

    public boolean updateStock(int productId, int newQuantity) {
        return productDAO.updateStock(productId, newQuantity);
    }
}