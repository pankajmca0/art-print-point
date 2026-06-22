CREATE TABLE stock_transfers (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id),
    from_branch_id INT NOT NULL REFERENCES branches(id),
    to_branch_id INT NOT NULL REFERENCES branches(id),
    quantity INT NOT NULL,
    status VARCHAR(20) DEFAULT 'COMPLETED',
    transferred_by INT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
