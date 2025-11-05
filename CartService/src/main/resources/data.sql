-- Drop the cart_items table
DROP TABLE IF EXISTS cart_items;

-- Ensure the 'cart_items' table exists
CREATE TABLE IF NOT EXISTS cart_items
(
    id              UUID PRIMARY KEY,
    product_id      UUID                NOT NULL,
    customer_id     UUID                NOT NULL,
    count           INT                 NOT NULL
);

-- Insert well-known UUIDs for specific products
INSERT INTO cart_items (id, product_id, customer_id, count)
VALUES ('412b1ca6-f97a-4d8d-8608-dc43b9e89e1f',
        '123e4567-e89b-12d3-a456-426614174000',
        '223e4567-e89b-12d3-a456-426614174008',
        1);

INSERT INTO cart_items (id, product_id, customer_id, count)
VALUES ('412b1ca6-f97a-4d8d-8608-dc43b9e89e2f',
        '123e4567-e89b-12d3-a456-426614174003',
        '223e4567-e89b-12d3-a456-426614174008',
        3);

INSERT INTO cart_items (id, product_id, customer_id, count)
VALUES ('412b1ca6-f97a-4d8d-8608-dc43b9e89e3f',
        '123e4567-e89b-12d3-a456-426614174001',
        '223e4567-e89b-12d3-a456-426614174009',
        1);