CREATE TABLE IF NOT EXISTS ingredient_price (
    price_id bigint PRIMARY KEY,
    ingredient_id bigint REFERENCES ingredient(ingredient_id),
    unit_price NUMERIC(10,2) not null,
    price_date timestamp not null,
    UNIQUE (ingredient_id, price_date)
);
