INSERT INTO users (email, password, first_name, last_name, city, sex, is_blocked, created_at)
VALUES ('organizer@example.com', '{bcrypt}password_hash', 'John', 'Doe', 'New York', 'M', false, NOW());

INSERT INTO users_roles (user_id, role_id)
VALUES (
           (SELECT id FROM users WHERE email = 'organizer@example.com'),
           (SELECT id FROM roles WHERE role = 'ROLE_ORGANIZER')
       );
