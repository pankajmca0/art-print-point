CREATE TABLE inventory_log (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES products(id),
    branch_id INT REFERENCES branches(id),
    movement_type VARCHAR(10) NOT NULL, -- IN or OUT
    quantity INT NOT NULL,
    reference_type VARCHAR(20), -- PURCHASE, SALE, TRANSFER
    reference_id INT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_inventory_log_product ON inventory_log(product_id);
CREATE INDEX idx_inventory_log_branch ON inventory_log(branch_id);
