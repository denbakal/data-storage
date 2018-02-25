create database camtest;

CREATE TABLE base_table (
	id           	bigint NOT NULL,
  version_table   bigint NOT NULL,
  valid_from   	timestamptz NOT NULL,
	valid_to        timestamptz NOT NULL,
	PRIMARY KEY(id)
);

CREATE SEQUENCE base_table_id_seq INCREMENT BY 50 START WITH 1;
ALTER TABLE ONLY base_table ALTER COLUMN id SET DEFAULT nextval('base_table_id_seq'::regclass);

CREATE TABLE base_field (
  id bigint NOT NULL,
  base_table_id bigint NOT NULL,
  ordinal int NOT NULL,
  name varchar(400) NOT NULL,
  start_date timestamptz NULL,
	end_date timestamptz NULL,
  is_active int NULL default 1,
  is_delete int NULL default 0,
	PRIMARY KEY(id)
);

CREATE SEQUENCE base_field_id_seq INCREMENT BY 50 START WITH 1;
ALTER TABLE ONLY base_field ALTER COLUMN id SET DEFAULT nextval('base_field_id_seq'::regclass);

insert into base_field(id, base_table_id, ordinal, name) values (1, 1, 1, 'FROM');
insert into base_field(id, base_table_id, ordinal, name) values (2, 1, 2, 'TO');
insert into base_field(id, base_table_id, ordinal, name) values (3, 1, 3, 'SERVICE');

CREATE TABLE base_lane (
  id bigint NOT NULL,
  base_table_id bigint NOT NULL,
  ordinal int NOT NULL,
  start_date timestamptz NULL,
	end_date timestamptz NULL,
  is_active int NULL default 1,
  is_delete int NULL default 0,
	PRIMARY KEY(id)
);

CREATE SEQUENCE base_lane_id_seq INCREMENT BY 50 START WITH 1;
ALTER TABLE ONLY base_lane ALTER COLUMN id SET DEFAULT nextval('base_lane_id_seq'::regclass);

CREATE TABLE base_field_value (
  id bigint NOT NULL,
  base_table_id bigint NOT NULL,
  lane_id bigint NOT NULL,
  field_id bigint NOT NULL,
  value varchar(400) NULL,
  start_date timestamptz NULL,
	end_date timestamptz NULL,
  is_active int NULL default 1,
  is_delete int NULL default 0,
	PRIMARY KEY(id)
);

CREATE SEQUENCE base_field_value_id_seq INCREMENT BY 50 START WITH 1;
ALTER TABLE ONLY base_field_value ALTER COLUMN id SET DEFAULT nextval('base_field_value_id_seq'::regclass);

CREATE TABLE lane_value (
    id bigint NOT NULL,
    lane_value jsonb NOT NULL,
    PRIMARY KEY(id)
);

CREATE OR REPLACE FUNCTION store_lane_values (in lanes text[]) RETURNS void AS
'
    DECLARE
     i bigint;
    BEGIN
      i=1;
      while i<= array_length(lanes, 1)
        LOOP
         INSERT INTO lane_value(id, lane_value) VALUES (i, lanes[i]::jsonb);
         i=i+1;
      END LOOP;
    END;
    '
LANGUAGE 'plpgsql';