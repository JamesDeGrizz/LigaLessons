create table parcelloader.parcel(
    id          serial    constraint parcelloader_pk_parcel primary key,
    name        text      not null,
    content     text      not null,
    symbol      char(1)   not null
);

create unique index parcelloader_parcel_name_un
    on parcelloader.parcel (name);

insert into parcelloader.parcel
    (name, content, symbol)
values
    ('тип 1', 'X', '1'),
    ('тип 2', 'XX', '2'),
    ('тип 3', 'XXX', '3'),
    ('тип 4', 'XXXX', '4'),
    ('тип 5', 'XXXXX', '5'),
    ('тип 6', 'XXX,XXX', '6'),
    ('тип 7', 'XXX,XXXX', '7'),
    ('тип 8', 'XXXX,XXXX', '8'),
    ('тип 9', 'XXX,XXX,XXX', '9');