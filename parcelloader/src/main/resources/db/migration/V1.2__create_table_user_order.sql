create table parcelloader.user_order(
    id              text        constraint parcelloader_pk_user_order primary key,
    date            timestamp   not null,
    operation       text        not null,
    trucks_count    int         not null,
    parcels_count   int         not null,
    total_price     int         not null
);