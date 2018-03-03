CREATE KEYSPACE datastore WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE tabledata (
  table_id int,
  version_id int,
  field_name text,
  row_index int,
  value text,
  PRIMARY KEY ((table_id, version_id), field_name, row_index));
