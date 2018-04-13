package ua.challenge.service.impl;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.aspect.Loggable;
import ua.challenge.dto.PersonDto;
import ua.challenge.entity.elasticsearch.Book;
import ua.challenge.entity.elasticsearch.PersonIndex;
import ua.challenge.mapper.PersonIndexMapper;
import ua.challenge.mapper.PersonMapper;
import ua.challenge.repository.PersonRepository;
import ua.challenge.repository.elasticsearch.PersonIndexRepository;
import ua.challenge.service.PersonService;
import ua.challenge.util.PersonGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Log4j2
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonIndexRepository indexRepository;

    @Autowired
    private PersonIndexMapper personIndexMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private AtomicInteger currentItem = new AtomicInteger();

    @Override
    public void save(PersonDto personDto) {

    }

    @Override
    @SneakyThrows
    @Loggable
    @Transactional
    public void init(long size) {
        long start = 0;
        currentItem.set(0);

        log.debug("Initializing database for {} persons", size);

        List<PersonIndex> personIndexList = new ArrayList<>();

        PersonDto joe = PersonGenerator.personGenerator();
        joe.setName("Joe Smith");
        joe.getAddress().setCountry("France");
        joe.getAddress().setCity("Paris");
        PersonDto persistJoe = this.personMapper.fromPerson(this.personRepository.save(this.personMapper.toPerson(joe)));
//        this.indexRepository.save(this.personIndexMapper.toPersonIndex(persistJoe));
        personIndexList.add(this.personIndexMapper.toPersonIndex(persistJoe));
        currentItem.incrementAndGet();

        PersonDto franceGall = PersonGenerator.personGenerator();
        franceGall.setName("France Gall");
        franceGall.setGender("female");
        franceGall.getAddress().setCountry("Italy");
        franceGall.getAddress().setCity("Ischia");
        PersonDto persistFranceGall = this.personMapper.fromPerson(this.personRepository.save(this.personMapper.toPerson(franceGall)));
//        this.indexRepository.save(this.personIndexMapper.toPersonIndex(persistFranceGall));
        personIndexList.add(this.personIndexMapper.toPersonIndex(persistFranceGall));
        currentItem.incrementAndGet();

        for (int i = 2; i < size; i++) {
            PersonDto person = PersonGenerator.personGenerator();
            PersonDto persistPerson = this.personMapper.fromPerson(this.personRepository.save(this.personMapper.toPerson(person)));
//            this.indexRepository.save(this.personIndexMapper.toPersonIndex(persistPerson));
            personIndexList.add(this.personIndexMapper.toPersonIndex(persistPerson));
            currentItem.incrementAndGet();
        }

        //bulk index
        this.indexRepository.save(personIndexList);

        long took = System.currentTimeMillis() - start;

        log.debug("Database initialized with {} persons. Took: {} ms, around {} per second.",
                size, took, 1000 * size / took);
    }

    @Override
    public List<PersonDto> getPersons() {
        return this.personMapper.fromPersonList(this.personRepository.findAll());
    }

    @Override
    @Loggable
    public List<PersonDto> search(String searchText) {
        QueryBuilder queryBuilder;

        if (Strings.isEmpty(searchText)) {
            queryBuilder = QueryBuilders.matchAllQuery();
        } else {
            queryBuilder = QueryBuilders.multiMatchQuery(searchText)
                    .field("name")
                    .field("gender")
                    .field("address.country")
                    .field("address.city")
                    .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
        }

        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        return this.personIndexMapper.fromPersonIndexList(this.elasticsearchTemplate.queryForList(query, PersonIndex.class));
    }

    @Override
    public List<PersonDto> advancedSearch(String name, String country, String city) {
        return this.personMapper.fromPersonList(this.personRepository.search(name, country, city));
    }
}
