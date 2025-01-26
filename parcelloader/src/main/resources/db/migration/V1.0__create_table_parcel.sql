create table parcelloader.parcel(
    name        text      constraint parcelloader_pk_parcel primary key,
    content     text      not null
);

insert into parcelloader.parcel
    (name, content)
values
    ('тип 1', '1'),
    ('тип 2', '22'),
    ('тип 3', '333'),
    ('тип 4', '4444'),
    ('тип 5', '55555'),
    ('тип 6', '666,666'),
    ('тип 7', '777,7777'),
    ('тип 8', '8888,8888'),
    ('тип 9', '999,999,999');