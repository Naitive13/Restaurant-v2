create type statusType as ENUM ('CREATED','CONFIRMED','IN_PROGRESS','DONE','DELIVERED');
create table if not exists order_status (
    status_id bigint PRIMARY KEY,
    order_reference varchar(20) references "order"(order_reference),
    order_status statusType not null,
    creation_date timestamp not null,
    unique (order_reference, order_status)
);