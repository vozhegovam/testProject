create sequence request_ex_seq_gen start 1 increment 1;

create table request_storage (
        id int8 not null,
        code varchar(30),
        error varchar(2048),
        file_name varchar(2048),
        number int8,
        primary key (id)
        );
