# data-storage

# Docker:

<h3>PostgreSQL:</h3>
docker run --name data-postgres -e POSTGRES_PASSWORD=1234 -d -p 5432:5432 postgres:9.6.7
docker run -it --rm --link data-postgres:postgres postgres:9.6.7 psql -h postgres -U postgres

<h3>Cassandra:</h3>
docker run --name data-cassandra -d -p 9042:9042 cassandra
docker run -it --link data-cassandra:cassandra --rm cassandra cqlsh cassandra

<h3>Redis:</h3>
docker run --name data-redis -d -p 6379:6379 redis redis-server --appendonly yes
docker run -it --link data-redis:redis --rm redis redis-cli -h redis -p 6379

Commands:
CONFIG GET databases

<h3>MongoDB:</h3>
docker run --name data-mongo -d -p 27017:27017 mongo
docker exec -it data-mongo bash
mongo

Commands:
show dbs
use testdb
db
show collections
db.createCollection("accounts")
db.users.find()
db.stats()
db.users.find().limit(3)
db.users.find().skip(3)
var cursor = db.users.find(); null;
db.users.count()
db.users.drop()
