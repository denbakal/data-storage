package ua.challenge.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.challenge.entity.elasticsearch.PersonIndex;

public interface PersonIndexRepository extends ElasticsearchRepository<PersonIndex, String> {
}
