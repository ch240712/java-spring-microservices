-- Drop the products table
DROP TABLE IF EXISTS products;

-- Ensure the 'products' table exists
CREATE TABLE IF NOT EXISTS products
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(255)        NOT NULL,
    description     VARCHAR(255)        NOT NULL,
    price           NUMERIC(10,2)       NOT NULL,
    currency        VARCHAR(3)          DEFAULT 'USD',
    quantity        INT                 NOT NULL
);

-- Insert well-known UUIDs for specific products
INSERT INTO products (id, name, description, price, quantity)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'Sony PS5',
       'Sony PS5 game console',
       '399.00',
       6
WHERE NOT EXISTS (SELECT 1
                  FROM products
                  WHERE id = '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO products (id, name, description, price, quantity)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'Vintage Lamp',
       'Vintage table lamp',
       '15.00',
       2
WHERE NOT EXISTS (SELECT 1
                  FROM products
                  WHERE id = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO products (id, name, description, price, quantity)
SELECT '123e4567-e89b-12d3-a456-426614174002',
       'Dell Inspiron Laptop',
       'Dell Inspiron laptop with 16 inch display',
       '1359.95',
       10
WHERE NOT EXISTS (SELECT 1
                  FROM products
                  WHERE id = '123e4567-e89b-12d3-a456-426614174002');

INSERT INTO products (id, name, description, price, quantity)
SELECT '123e4567-e89b-12d3-a456-426614174003',
       'Blue Horizon Tent',
       'Blue Horizon one-man tent',
       '199.99',
       25
WHERE NOT EXISTS (SELECT 1
                  FROM products
                  WHERE id = '123e4567-e89b-12d3-a456-426614174003');

INSERT INTO products (id, name, description, price, quantity)
SELECT '123e4567-e89b-12d3-a456-426614174004',
       'Sonic Youth Speaker',
       'Sonic Youth super boom-boom speaker',
       '19.99',
       19
WHERE NOT EXISTS (SELECT 1
                  FROM products
                  WHERE id = '123e4567-e89b-12d3-a456-426614174004');