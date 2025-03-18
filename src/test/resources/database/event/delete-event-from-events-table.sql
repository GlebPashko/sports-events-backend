DELETE FROM events_categories;
ALTER TABLE events_categories AUTO_INCREMENT  = 1;

DELETE FROM categories;
ALTER TABLE categories AUTO_INCREMENT = 1;

DELETE FROM cart_items;
ALTER TABLE cart_items AUTO_INCREMENT = 1;

-- DELETE FROM shopping_carts;
-- ALTER TABLE shopping_carts AUTO_INCREMENT = 1;

DELETE FROM events;
ALTER TABLE events AUTO_INCREMENT = 1;