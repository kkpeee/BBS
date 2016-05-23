create table users(
id integer primary key not null auto_increment,
login_id varchar(20) not null,
password varchar(255) not null,
name varchar(10) not null,
branch_id integer not null,
department_id integer not null,
account_op ,
);

create table branches(
id integer primary key not null auto_increment,
name varchar(20) not null
);


create table departments(
id integer primary key not null auto_increment,
name varchar(20) not null
);

create table contributions(
id integer primary key not null auto_increment,
title varchar(50) not null,
text text not null,
category varchar(10) not null,
user_id integer not null,
text varchar(500) not null,
insert_date timestamp not null,
update_date timestamp not null
);

create table comments(
id integer primary key not null auto_increment,
text text not null,
user_id integer not null,
insert_date timestamp not null,
update_date timestamp not null
);