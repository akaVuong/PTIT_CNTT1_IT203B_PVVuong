DROP DATABASE IF EXISTS flash_sale_engine;
CREATE DATABASE flash_sale_engine CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE flash_sale_engine;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(150) NOT NULL,
    category_id INT NOT NULL,
    stock INT NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    is_flash_sale BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_products_stock CHECK (stock >= 0),
    CONSTRAINT chk_products_price CHECK (price >= 0),
    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(14, 2) NOT NULL,
    status ENUM('SUCCESS', 'CANCELLED', 'FAILED') NOT NULL DEFAULT 'SUCCESS',
    CONSTRAINT chk_orders_total_amount CHECK (total_amount >= 0),
    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_details (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL,
    line_total DECIMAL(14, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    CONSTRAINT chk_order_details_quantity CHECK (quantity > 0),
    CONSTRAINT chk_order_details_unit_price CHECK (unit_price >= 0),
    CONSTRAINT fk_order_details_order
        FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_order_details_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_flash_sale ON products(is_flash_sale);
CREATE INDEX idx_orders_user_date ON orders(user_id, order_date);
CREATE INDEX idx_order_details_order ON order_details(order_id);
CREATE INDEX idx_order_details_product ON order_details(product_id);

INSERT INTO categories (category_name) VALUES
('Electronics'),
('Fashion'),
('Home');

INSERT INTO users (full_name, address) VALUES
('Nguyen Van A', 'Ho Chi Minh City'),
('Tran Thi B', 'Ha Noi'),
('Le Van C', 'Da Nang');

INSERT INTO products (product_name, category_id, stock, price, is_flash_sale) VALUES
('Wireless Mouse', 1, 10, 299000.00, TRUE),
('Mechanical Keyboard', 1, 5, 1299000.00, TRUE),
('Flash Sale T-Shirt', 2, 20, 199000.00, TRUE),
('Air Fryer', 3, 8, 1899000.00, FALSE);

DELIMITER //

CREATE PROCEDURE sp_get_top_buyers(IN p_limit INT)
BEGIN
    SELECT
        u.user_id,
        u.full_name,
        COUNT(DISTINCT o.order_id) AS total_orders,
        COALESCE(SUM(od.quantity), 0) AS total_items_bought,
        COALESCE(SUM(od.line_total), 0) AS total_spent
    FROM users u
    LEFT JOIN orders o
        ON u.user_id = o.user_id
        AND o.status = 'SUCCESS'
    LEFT JOIN order_details od
        ON o.order_id = od.order_id
    GROUP BY u.user_id, u.full_name
    ORDER BY total_items_bought DESC, total_spent DESC, u.user_id ASC
    LIMIT p_limit;
END //

CREATE PROCEDURE sp_get_category_revenue()
BEGIN
    SELECT
        c.category_id,
        c.category_name,
        COALESCE(SUM(od.line_total), 0) AS total_revenue
    FROM categories c
    LEFT JOIN products p
        ON c.category_id = p.category_id
    LEFT JOIN order_details od
        ON p.product_id = od.product_id
    LEFT JOIN orders o
        ON od.order_id = o.order_id
        AND o.status = 'SUCCESS'
    GROUP BY c.category_id, c.category_name
    ORDER BY total_revenue DESC, c.category_id ASC;
END //

CREATE FUNCTION fn_calculate_category_revenue(p_category_id INT)
RETURNS DECIMAL(14, 2)
READS SQL DATA
BEGIN
    DECLARE v_total DECIMAL(14, 2);

    SELECT COALESCE(SUM(od.line_total), 0)
    INTO v_total
    FROM order_details od
    INNER JOIN orders o
        ON od.order_id = o.order_id
    INNER JOIN products p
        ON od.product_id = p.product_id
    WHERE o.status = 'SUCCESS'
      AND p.category_id = p_category_id;

    RETURN COALESCE(v_total, 0);
END //

DELIMITER ;
