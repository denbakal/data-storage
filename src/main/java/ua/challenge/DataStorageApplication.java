package ua.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class})
@EnableAspectJAutoProxy
public class DataStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataStorageApplication.class, args);
    }
}
