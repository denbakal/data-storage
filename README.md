# data-storage
ssandra:</h3>
docker run --name data-cassandra -d -p 9042:9042 cassandra
docker run -it --link data-cassandra:cassandra --rm cassandra cqlsh cassandra

CREATE KEYSPACE IgniteTest WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

USE IgniteTest;

CREATE TABLE catalog_category (id bigint primary key, parentId bigint, name text, description text);
CREATE TABLE catalog_good (id bigint primary key, categoryId bigint, name text, description text, price bigint, oldPrice bigint);

INSERT INTO catalog_category (id, parentId, name, description) VALUES (1, NULL, 'Бытовая техника', 'Различная бытовая техника для вашего дома!');
INSERT INTO catalog_category (id, parentId, name, description) VALUES (2, 1, 'Холодильники', 'Самые холодные холодильники!');
INSERT INTO catalog_category (id, parentId, name, description) VALUES (3, 1, 'Стиральные машинки', 'Замечательные стиралки!');

INSERT INTO catalog_good (id, categoryId, name, description, price, oldPrice) VALUES (1, 2, 'Холодильник Buzzword', 'Лучший холодильник 2027!', 1000, NULL);
INSERT INTO catalog_good (id, categoryId, name, description, price, oldPrice) VALUES (2, 2, 'Холодильник Foobar', 'Дешевле не найти!', 300, 900);
INSERT INTO catalog_good (id, categoryId, name, description, price, oldPrice) VALUES (3, 2, 'Холодильник Barbaz', 'Люкс на вашей кухне!', 500000, 300000);
INSERT INTO catalog_good (id, categoryId, name, description, price, oldPrice) VALUES (4, 3, 'Машинка Habr', 'Стирает, отжимает, сушит!', 10000, NULL);

SQL:
SELECT cg.name goodName, cg.price goodPrice, cc.name category, pcc.name parentCategory
FROM catalog_category.CatalogCategory cc
  JOIN catalog_category.CatalogCategory pcc
  ON cc.parentId = pcc.id
  JOIN catalog_good.CatalogGood cg
  ON cg.categoryId = cc.id;
  
SELECT cc.name, AVG(cg.price) avgPrice
FROM catalog_category.CatalogCategory cc
  JOIN catalog_good.CatalogGood cg
  ON cg.categoryId = cc.id
WHERE cg.price <= 100000
GROUP BY cc.id;  