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

CREATE TABLE table_data_lane  (
	id          	bigserial NOT NULL,
	table_data_id		int8 NOT NULL,
	ordinal     	int4 NOT NULL,
	lane_hash text NULL,
	PRIMARY KEY(id)
);
CREATE SEQUENCE table_data_lane_id_seq INCREMENT BY 1 START WITH 1;
ALTER TABLE ONLY table_data_lane ALTER COLUMN id SET DEFAULT nextval('table_data_lane_id_seq'::regclass);

CREATE TABLE table_data_lane_field  (
	id          	bigserial NOT NULL,
	lane_id		int8 NOT NULL,
	table_data_id		int8 NOT NULL,
	values jsonb NOT NULL,
	PRIMARY KEY(id)
);
CREATE SEQUENCE table_data_lane_field_id_seq INCREMENT BY 1 START WITH 1;
ALTER TABLE ONLY table_data_lane_field ALTER COLUMN id SET DEFAULT nextval('table_data_lane_field_id_seq'::regclass);

DROP TABLE table_data_lane_price;
CREATE TABLE table_data_lane_price  (
	id          	bigserial NOT NULL,
	lane_id		    int8 NOT NULL,
	table_data_id		int8 NOT NULL,
	price_id		int8 NOT NULL,
	values jsonb NULL,
	PRIMARY KEY(id)
);
CREATE SEQUENCE table_data_lane_price_id_seq INCREMENT BY 1 START WITH 1;
ALTER TABLE ONLY table_data_lane_price ALTER COLUMN id SET DEFAULT nextval('table_data_lane_price_id_seq'::regclass);

CREATE OR REPLACE FUNCTION store_values (in lanes text[], IN table_data_id bigint, IN prices bigint[]) RETURNS void AS
$BODY$
    DECLARE
     i bigint;
		 j bigint;
		 price text;
		 current_price_value text;
		 price_search_param text[];
    BEGIN
      i=1;
	    WHILE i <= array_length(lanes, 1)
		  LOOP
			  INSERT INTO table_data_lane(id, table_data_id, ordinal, lane_hash) VALUES (nextval('table_data_lane_id_seq'), i, table_data_id, encode(digest(table_data_id || lanes[i], 'sha1'), 'hex'));
        INSERT INTO table_data_lane_field(id, lane_id, table_data_id, values) VALUES (nextval('table_data_lane_field_id_seq'), currval('table_data_lane_id_seq'), table_data_id, lanes[i]::jsonb->'fields');

			  j=1;
			  price = lanes[i]::jsonb->'prices';

			  IF (coalesce(price, '') != '') THEN
			    price_search_param[0] = 'prices';

			    WHILE j <= array_length(prices, 1)
			      LOOP
			        price_search_param[1] = prices[j];
			        current_price_value = lanes[i]::jsonb#>price_search_param;

              IF (coalesce(current_price_value, '') != '') THEN
                  INSERT INTO table_data_lane_price(id, lane_id, table_data_id, price_id, values)
                  VALUES (nextval('table_data_lane_price_id_seq'), currval('table_data_lane_id_seq'), table_data_id, prices[j], current_price_value::jsonb);
              END IF;
              j= j+1;
            END LOOP;
			  END IF;
		    i= i+1;
	    END LOOP;
    END;
$BODY$
LANGUAGE 'plpgsql';
DROP FUNCTION store_values(text[], bigint, bigint[]);