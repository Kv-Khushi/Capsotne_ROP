-- Create the "users" table with columns that correspond to the fields in the User entity
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone_number BIGINT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    wallet DOUBLE,
    user_role VARCHAR(255) NOT NULL
);

-- Add the unique constraint on the user_email column
ALTER TABLE users ADD CONSTRAINT unique_user_email UNIQUE (user_email);


-- Create the "address" table with columns corresponding to a typical Address entity
CREATE TABLE IF NOT EXISTS address (
    address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Add a unique constraint to prevent duplicate addresses for the same user
ALTER TABLE address ADD CONSTRAINT unique_user_address UNIQUE (user_id, street, city, state, zip_code, country);


