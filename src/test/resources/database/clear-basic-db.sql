SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM cart_items;
ALTER TABLE cart_items AUTO_INCREMENT = 1;

DELETE FROM event_participants;
ALTER TABLE event_participants AUTO_INCREMENT = 1;

DELETE FROM order_items;
ALTER TABLE order_items AUTO_INCREMENT = 1;

DELETE FROM orders;
ALTER TABLE orders AUTO_INCREMENT = 1;

DELETE FROM payments;
ALTER TABLE payments AUTO_INCREMENT = 1;

-- DELETE FROM shopping_carts;
-- ALTER TABLE shopping_carts AUTO_INCREMENT = 1;

DELETE FROM events;
ALTER TABLE events AUTO_INCREMENT = 1;

DELETE FROM events_categories;
ALTER TABLE events_categories AUTO_INCREMENT = 1;

DELETE FROM categories;
ALTER TABLE categories AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;
