drop table if exists entry;
create table entry(
  id INTEGER NOT NULL AUTO_INCREMENT,
  title VARCHAR(255),
  summary VARCHAR(255),
  created TIMESTAMP,
  PRIMARY KEY (id)
);