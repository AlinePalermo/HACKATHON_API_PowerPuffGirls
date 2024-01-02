
DROP DATABASE IF EXISTS stock;
CREATE DATABASE stock;
USE stock;

CREATE TABLE items (
    item_id INTEGER PRIMARY KEY,
    item_name CHAR(20),
    items_in_stock INTEGER,
    price NUMERIC(4,2)

);


INSERT INTO items(item_id, item_name, items_in_stock, price) VALUES (1, 'T-Shirt', 20, 24.99);
INSERT INTO items(item_id, item_name, items_in_stock, price) VALUES (2, 'Mug', 20, 9.99);
INSERT INTO items(item_id, item_name, items_in_stock, price) VALUES (3, 'Key_chain', 20, 4.99);
