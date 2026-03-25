package dao;

import models.OrderItem;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public void placeOrder(int userId, List<OrderItem> items) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Kiểm tra tồn kho
            String sql1 = "select product_name, stock from products where id = ?";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            for (OrderItem item : items) {
                statement1.setInt(1, item.getProductId());
                ResultSet resultSet = statement1.executeQuery();
                if (resultSet.next()) {
                    int stock = resultSet.getInt("stock");
                    String productName = resultSet.getString("product_name");
                    if (stock < item.getQuantity()) {
                        System.out.printf("Sản phẩm %s không đủ hàng\n", productName);
                        connection.rollback();
                        return;
                    }
                }
            }

            // Trừ tồn kho
            String updateStockSql = "update products set stock = stock - ? where id = ?";
            PreparedStatement statement2 = connection.prepareStatement(updateStockSql);
            for (OrderItem item : items) {
                statement2.setInt(1, item.getQuantity());
                statement2.setInt(2, item.getProductId());
                statement2.executeUpdate();
            }

            // Tạo order
            String orderSql = "insert into orders(user_id,order_date, total_amount) values (?, ?, ?)";
            PreparedStatement statement3 = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            // Tính tổng tiền cuả đơn hàng
            double totalAmount = 0;
            String getPriceSql = "select price from products where id = ?";
            for (OrderItem item : items) {
                PreparedStatement statement4 = connection.prepareStatement(getPriceSql);
                statement4.setInt(1, item.getProductId());
                ResultSet resultSet = statement4.executeQuery();
                if (resultSet.next()) {
                    double price = resultSet.getDouble("price");
                    totalAmount += price * item.getQuantity();
                }
            }
            statement3.setInt(1, userId);
            statement3.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement3.setDouble(3, totalAmount);
            statement3.executeUpdate();
            // Lấy orderId tự tăng
            ResultSet key = statement3.getGeneratedKeys();
            key.next();
            int orderId = key.getInt(1);
            // Tạo các Order Item
            insertOrderItemDetails(connection, orderId, items);
            connection.commit();
            System.out.println("Đặt hàng thành công");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void insertOrderItemDetails(Connection connection, int orderId, List<OrderItem> items) {
        String inserOrderItemSql = "insert into order_items(order_id, product_id, quantity) values (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(inserOrderItemSql);
            for (OrderItem item : items) {
                statement.setInt(1, orderId);
                statement.setInt(2, item.getProductId());
                statement.setInt(3, item.getQuantity());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
