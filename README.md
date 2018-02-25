# data-storage

Docker:
docker run --name data-postgres -e POSTGRES_PASSWORD=1234 -d -p 5432:5432 postgres:9.6.7
docker run -it --rm --link data-postgres:postgres postgres:9.6.7 psql -h postgres -U postgres
