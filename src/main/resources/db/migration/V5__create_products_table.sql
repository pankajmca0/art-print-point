CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    category_id INT NOT NULL REFERENCES categories(id),
    vendor_id INT REFERENCES vendors(id),
    branch_id INT REFERENCES branches(id),
    purchase_price DECIMAL(10,2) NOT NULL DEFAULT 0,
    selling_price DECIMAL(10,2) NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 0,
    unit VARCHAR(20) DEFAULT 'piece',
    barcode VARCHAR(100) UNIQUE,
    design_model_number VARCHAR(100),
    description TEXT,
    product_image VARCHAR(500),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    low_stock_threshold INT DEFAULT 10,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_barcode ON products(barcode);
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_branch ON products(branch_id);
