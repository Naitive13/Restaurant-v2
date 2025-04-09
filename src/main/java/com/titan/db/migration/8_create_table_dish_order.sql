create table if not exists dish_order (
    dish_order_id bigint primary key,
    dish_id bigint references dish(dish_id) not null,
    order_reference varchar(20) references "order"(order_reference) not null,
    quantity int not null,
    UNIQUE (dish_id, order_reference)
);