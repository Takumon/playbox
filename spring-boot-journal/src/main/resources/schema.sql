drop table journal if exists;
create table journal(id SERIAL, title VARCHAR(255), summary VARCHAR(255), created TIMESTAMP)