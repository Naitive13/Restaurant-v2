create type movement_type as ENUM ('IN','OUT');

CREATE TABLE IF NOT EXISTS stock (
    ingredient_id bigint REFERENCES ingredient(ingredient_id),
    quantity NUMERIC(10,2) not null,
    movement movement_type not null,
    last_modified timestamp not null
);


