CREATE TABLE IF NOT EXISTS accidents (
  id serial primary key,
  name text,
  text text,
  address text,
  type_id int references types (id)
);