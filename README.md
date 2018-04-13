# data-storage

docker run -d --name=data-elasticsearch -p 9200:9200 -p 9300:9300 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" elasticsearch:2.4.6 -Des.node.name="TestNode"
docker exec -it data-elasticsearch bash

docker network create data-network --driver=bridge
docker run -d --name=data-elasticsearch --network data-network -p 9200:9200 -p 9300:9300 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" elasticsearch:2.4.6 -Des.node.name="TestNode"

docker run -d --name data-kibana --network data-network -e ELASTICSEARCH_URL=http://data-elasticsearch:9200 -p 5601:5601 kibana:4.6
docker run -d -e ELASTICSEARCH_URL=http://elasticsearch_2_4:9200 -p 5601:5601 -d kibana:4.6

localhost:9200/_cluster/health

1) Create a new index:
PUT /bookdb_index
{ "settings": { "number_of_shards": 1 }}

) Get Settings:
GET localhost:9200/bookdb_index/_settings

) Perform a manual flush:
POST /books/_flush

) Filtered query:
{
  "query": {
    "bool": {
      "filter": {
        "term": {
          "books.publisher": "penguin"
        }
      }
    }
  }
}

) Nested
PUT my_index/_mapping/nested_author
{
  "properties": {
    "name": {
      "type": "text"
    },
    "books": {
      "type": "nested"
    }
  }
}

PUT my_index/nested_author/1
{
  "name" : "Multi G. Enre",
  "books": [
    {
      "name": "Guns and lasers",
      "genre": "scifi",
      "publisher": "orbit"
    },
    {
      "name": "Dead in the night",
      "genre": "thriller",
      "publisher": "penguin"
    }
  ]
}

GET my_index/_search



2) Index some documents using the bulk API:
POST /bookdb_index/book/_bulk

3) Get Mappins:
GET localhost:9200/bookdb_index/_mapping

3) Basic Match Query:
GET /bookdb_index/book/_search?q=guide

- specify what fields you want to search on:
GET /bookdb_index/book/_search?q=title:in action

4) Delete Index
DELETE /bookdb_index

5) Put Mapping
PUT twitter
{
  "mappings": {
    "tweet": {
      "properties": {
        "message": {
          "type": "text"
        }
      }
    }
  }
}