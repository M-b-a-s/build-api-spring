-- Clear existing data (optional, for clean starts)
DELETE FROM users;
DELETE FROM products;
DELETE FROM categories;

-- Insert categories
INSERT INTO categories (name) VALUES
('Electronics'),
('Clothing'),
('Books'),
('Home & Garden'),
('Sports & Outdoors'),
('Beauty & Personal Care'),
('Toys & Games'),
('Automotive'),
('Health & Wellness'),
('Jewelry & Accessories');

-- Insert users
INSERT INTO users (name, email, password) VALUES
('John Smith', 'john.smith@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.KbVkYJ.6QaRk6Uq6iZ7tCw6Q2QYq/W'),
('Emma Johnson', 'emma.johnson@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.KbVkYJ.6QaRk6Uq6iZ7tCw6Q2QYq/W'),
('Michael Brown', 'michael.brown@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.KbVkYJ.6QaRk6Uq6iZ7tCw6Q2QYq/W'),
('Sarah Davis', 'sarah.davis@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.KbVkYJ.6QaRk6Uq6iZ7tCw6Q2QYq/W'),
('David Wilson', 'david.wilson@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.KbVkYJ.6QaRk6Uq6iZ7tCw6Q2QYq/W');

-- Insert products
INSERT INTO products (name, description, price, category_id) VALUES
-- Electronics (Category 1)
('iPhone 15 Pro', 'Latest Apple smartphone with A17 Pro chip', 999.99, 1),
('Samsung Galaxy S24', 'Advanced Android phone with AI features', 849.99, 1),
('Sony WH-1000XM5', 'Wireless noise-canceling headphones', 399.99, 1),
('MacBook Air M3', 'Lightweight laptop with Apple M3 chip', 1099.99, 1),
('iPad Air', 'Versatile tablet for work and entertainment', 599.99, 1),

-- Clothing (Category 2)
('Nike Air Max 270', 'Comfortable running shoes with Air cushioning', 129.99, 2),
('Levi''s 501 Jeans', 'Classic straight-fit denim jeans', 89.99, 2),
('Adidas Ultraboost', 'High-performance running shoes', 179.99, 2),
('North Face Jacket', 'Waterproof outdoor jacket', 199.99, 2),
('Uniqlo Cotton T-Shirt', 'Soft breathable cotton t-shirt', 14.99, 2),

-- Books (Category 3)
('The Midnight Library', 'Novel by Matt Haig about life choices', 12.99, 3),
('Atomic Habits', 'Self-help book by James Clear', 13.99, 3),
('Project Hail Mary', 'Science fiction novel by Andy Weir', 15.99, 3),
('The Hobbit', 'Fantasy classic by J.R.R. Tolkien', 10.99, 3),
('Dune', 'Science fiction masterpiece by Frank Herbert', 11.99, 3),

-- Home & Garden (Category 4)
('Dyson V15 Vacuum', 'Cordless stick vacuum cleaner', 749.99, 4),
('Instant Pot Duo', '7-in-1 electric pressure cooker', 99.99, 4),
('KitchenAid Mixer', 'Stand mixer for baking and cooking', 429.99, 4),
('YETI Tumbler', 'Insulated stainless steel mug', 39.99, 4),
('Philips Hue Starter Kit', 'Smart LED lighting system', 199.99, 4),

-- Sports & Outdoors (Category 5)
('Yoga Mat Premium', 'Non-slip exercise mat for yoga and fitness', 29.99, 5),
('Wilson Tennis Racket', 'Professional tennis racket', 189.99, 5),
('Coleman Tent', '4-person camping tent', 129.99, 5),
('Hydro Flask Water Bottle', 'Insulated stainless steel water bottle', 34.99, 5),
('Garmin GPS Watch', 'Fitness tracker with GPS', 299.99, 5);