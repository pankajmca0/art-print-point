CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(150) UNIQUE NOT NULL,
    contact_number VARCHAR(20),
    address TEXT,
    role VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_audit_log (
    id SERIAL PRIMARY KEY,
    user_id INT,
    action VARCHAR(50),
    login_time TIMESTAMP,
    logout_time TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);