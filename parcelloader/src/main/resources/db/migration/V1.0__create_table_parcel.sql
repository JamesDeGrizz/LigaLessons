create table parcelloader.parcel(
    id          serial    constraint parcelloader_pk_parcel primary key,
    name        text      not null,
    content     text      not null,
    symbol      char(1)   not null
);

create unique index parcelloader_parcel_name_un
    on parcelloader.parcel (name);