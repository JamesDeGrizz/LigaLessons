create table billing.user_order_inbox(
    name            text        not null,
    date            timestamp   not null,
    operation       text        not null,
    trucks_count    int         not null,
    parcels_count   int         not null,
    cells_count     int         not null,
    processed       boolean     not null
);

alter table billing.user_order_inbox
    add constraint billing_pk_user_order_inbox primary key (name, date, operation);