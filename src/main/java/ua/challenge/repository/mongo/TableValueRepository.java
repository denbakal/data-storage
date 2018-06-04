package ua.challenge.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.challenge.entity.mongo.TableValue;

//public interface TableValueRepository extends MongoRepository<TableValue, String> {
public interface TableValueRepository {
    TableValue findByTableId(Long id);
}
