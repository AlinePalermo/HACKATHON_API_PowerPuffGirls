DROP TABLE IF EXISTS items;

CREATE TABLE items(
    id INT PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    items_in_stock INT,
    price DECIMAL(10, 2)
);

INSERT INTO items (id, item_name, items_in_stock, price) VALUES
(1, 'Product A', 10, 99.99),
(2, 'Product B', 5, 49.99),
(3, 'Product C', 20, 129.99);