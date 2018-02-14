CREATE TABLE base_table (
	id           	bigint NOT NULL,
  version_table   bigint NOT NULL,
  valid_from   	timestamptz NOT NULL,
	valid_to        timestamptz NOT NULL,
	PRIMARY KEY(id)
);

CREATE SEQUENCE base_table_id_seq INCREMENT BY 50 START WITH 1;
ALTER TABLE ONLY base_table ALTER COLUMN id SET DEFAULT nextval('base_table_id_seq'::regclass);