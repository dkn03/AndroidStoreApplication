CREATE TABLE IF NOT EXISTS users(
    id INTEGER PRIMARY KEY,
    email TEXT,
    phone_number integer,
    password TEXT NOT NULL,
    name TEXT
);

CREATE TABLE IF NOT EXISTS products(
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    amount INTEGER NOT NULL CHECK(amount >= 0),
    price DOUBLE NOT NULL CHECK(price > 0),
    images TEXT --Список URL
    characteristics TEXT --Сделать json
);

CREATE TABLE IF NOT EXISTS cart(
    user_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    amount INTEGER DEFAULT 1 NOT NULL CHECK (amount > 0),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);