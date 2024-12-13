use malldb;

create table tbl_todo
(
    tno      bigint not null auto_increment,
    title    varchar(255),
    writer   varchar(255),
    complete bit    not null default false,
    due_date date,
    primary key (tno)
);