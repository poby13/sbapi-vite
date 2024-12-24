create table member_roles
(
    member_id bigint not null,
    role_id   bigint not null,
    primary key (member_id, role_id),
    constraint member_roles_ibfk_1
        foreign key (member_id) references tbl_members (id)
            on delete cascade,
    constraint member_roles_ibfk_2
        foreign key (role_id) references tbl_roles (id)
            on delete cascade
);

create index role_id
    on member_roles (role_id);

create table tbl_members
(
    id         bigint auto_increment
        primary key,
    email      varchar(255)                           not null,
    pw         varchar(255)                           not null,
    nickname   varchar(100)                           null,
    social     tinyint(1) default 0                   null,
    enabled    tinyint(1) default 1                   null,
    created_at timestamp  default current_timestamp() null,
    updated_at timestamp  default current_timestamp() null on update current_timestamp(),
    constraint email
        unique (email)
);

create index idx_email
    on tbl_members (email);

create table tbl_roles
(
    id        bigint auto_increment
        primary key,
    role_name varchar(50) not null,
    constraint role_name
        unique (role_name)
);

create index idx_role_name
    on tbl_roles (role_name);

