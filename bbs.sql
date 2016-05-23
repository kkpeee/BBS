create table users(
id integer primary key not null auto_increment,
login_id varchar(20) not null,
password varchar(255) not null,
name varchar(10) not null,
branch_id integer not null,
department_id integer not null,
locked integer default 0
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
created_at timestamp not null,
updated_at timestamp not null
);

create table comments(
id integer primary key not null auto_increment,
text text not null,
user_id integer not null,
contribution_id integer not null,
created_at timestamp not null,
updated_at timestamp not null
);