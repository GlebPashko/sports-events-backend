INSERT INTO users (email, password, first_name, last_name, city, sex, is_blocked, created_at)
VALUES ('user@example.com', '{bcrypt}password_hash', 'Tony', 'Soprano', 'ODESA', 'MALE', false, NOW());

INSERT INTO users_roles (user_id, role_id)
VALUES (
           (SELECT id FROM users WHERE email = 'user@example.com'),
           (SELECT id FROM roles WHERE role = 'ROLE_USER')
       );

INSERT INTO users (email, password, first_name, last_name, city, sex, is_blocked, created_at)
VALUES ('organizer@example.com', '{bcrypt}password_hash', 'Tony', 'Soprano', 'ODESA', 'MALE', false, NOW());

INSERT INTO users_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'organizer@example.com'), (SELECT id FROM roles WHERE role = 'ROLE_ORGANIZER')),
       ((SELECT id FROM users WHERE email = 'organizer@example.com'), (SELECT id FROM roles WHERE role = 'ROLE_USER'));

INSERT INTO users (email, password, first_name, last_name, city, sex, is_blocked, created_at)
VALUES ('admin@example.com', '{bcrypt}password_hash', 'Tony', 'Soprano', 'ODESA', 'MALE', false, NOW());

INSERT INTO users_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@example.com'), (SELECT id FROM roles WHERE role = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE email = 'admin@example.com'), (SELECT id FROM roles WHERE role = 'ROLE_ORGANIZER')),
       ((SELECT id FROM users WHERE email = 'admin@example.com'), (SELECT id FROM roles WHERE role = 'ROLE_USER'));

INSERT INTO shopping_carts(id) VALUES (1), (2), (3);