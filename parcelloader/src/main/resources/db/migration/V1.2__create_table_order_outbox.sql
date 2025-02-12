create table parcelloader.order_outbox (
    name          text      not null,
    date          timestamp not null,
    operation     text      not null,
    trucks_count  int       not null,
    parcels_count int       not null,
    cells_count   int       not null,
    sent          boolean   not null
);

alter table parcelloader.order_outbox
    add constraint parcelloader_pk_order_outbox primary key (name, date, operation);