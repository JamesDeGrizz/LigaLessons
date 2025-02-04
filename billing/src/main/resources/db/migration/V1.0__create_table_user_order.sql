create table billing.user_order(
    id              serial      constraint billing_pk_user_order primary key,
    name            text        not null,
    date            timestamp   not null,
    operation       text        not null,
    trucks_count    int         not null,
    parcels_count   int         not null,
    total_price     int         not null
);

create index billing_user_order_name_idx
    on billing.user_order (name);
