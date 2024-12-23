

-- Insert test data into the users table
INSERT INTO users (user_id, phone_number, user_name, user_email, user_password, wallet, user_role)
VALUES
(1, 1234567890, 'Test User', 'testuser@example.com', 'password123', 100.0, 'ROLE_USER');

-- Preload an address for user 1
INSERT INTO address (user_id, street, city, state, zip_code, country)
VALUES (1, '123 Main St', 'TestCity', 'IL', 62704, 'ABC');