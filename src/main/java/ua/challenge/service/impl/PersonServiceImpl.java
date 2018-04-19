package ua.challenge.service.impl;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.aspect.Loggable;
import ua.challenge.dto.BucketDto;
import ua.challenge.dto.PersonDto;
import ua.challenge.dto.SearchResultDto;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    public SearchResultDto search(String searchText) {
        SearchResultDto result = new SearchResultDto();
        QueryBuilder queryBuilder;

        if (Strings.isEmpty(searchText)) {
            queryBuilder = QueryBuilders.matchAllQuery();
        } else {
            queryBuilder = QueryBuilders.multiMatchQuery(searchText)
                    .field("name.autocomplete")
                    .field("gender.autocomplete")
                    .field("address.country.autocomplete")
                    .field("address.city.autocomplete")
                    .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
//                .operator(MatchQueryBuilder.Operator.AND)
//                    .fuzziness(Fuzziness.ONE)
//                    .prefixLength(3)
            ;
        }

        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(
                        AggregationBuilders.terms("by_country")
                                .field("address.country")
//                                .order(Terms.Order.aggregation("_count", false))
                )
                .addAggregation(
                        AggregationBuilders.dateHistogram("by_year")
                                .field("dateOfBirth")
                                .minDocCount(0)
                                .interval(DateHistogramInterval.days(3652))
                                .extendedBounds("1940", "2009")
                                .format("YYYY")
                )
                .build();

        log.debug("Search query: {}", query.getQuery().toString());

        Aggregations aggregations = elasticsearchTemplate.query(query, SearchResponse::getAggregations);

        log.debug("Aggregations: {}", aggregations.asList().size());

        Map<String, Aggregation> results = aggregations.asMap();
        StringTerms countries = (StringTerms) results.get("by_country");
        InternalHistogram years = (InternalHistogram) results.get("by_year");

        List<BucketDto> byCountries = countries.getBuckets().stream()
                .map(bucket -> new BucketDto(bucket.getKeyAsString(), bucket.getDocCount()))
                .collect(Collectors.toList());
        result.setCountries(byCountries);
        log.debug("byCountries: {}", byCountries);

        List<? extends Histogram.Bucket> yearBuckets = ((Histogram) years).getBuckets();
        List<BucketDto> byYears = yearBuckets.stream()
                .map(bucket -> new BucketDto(bucket.getKeyAsString(), bucket.getDocCount()))
                .collect(Collectors.toList());
        result.setYears(byYears);
        log.debug("byYears: {}", byYears);

        result.setPersons(this.personIndexMapper.fromPersonIndexList(this.elasticsearchTemplate.queryForList(query, PersonIndex.class)));
        return result;
    }

    @Override
    @Loggable
    public List<PersonDto> advancedSearch(String name, String country, String city) {
        QueryBuilder queryBuilder;

        if (Strings.isEmpty(name) && Strings.isEmpty(country) && Strings.isEmpty(city)) {
            queryBuilder = QueryBuilders.matchAllQuery();
        } else {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            if (Strings.hasText(name)) {
                boolQueryBuilder.must(
                        QueryBuilders.matchQuery("name.autocomplete", name)
                );
            }

            if (Strings.hasText(country)) {
                boolQueryBuilder.must(
                        QueryBuilders.matchQuery("address.country.autocomplete", country)
                );
            }

            if (Strings.hasText(city)) {
                boolQueryBuilder.must(
                        QueryBuilders.matchQuery("address.city.autocomplete", city)
                );
            }

            queryBuilder = boolQueryBuilder;
        }

        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        log.debug("Advanced Search query: {}", query.getQuery().toString());

//        return this.personMapper.fromPersonList(this.personRepository.search(name, country, city));
        return this.personIndexMapper.fromPersonIndexList(this.elasticsearchTemplate.queryForList(query, PersonIndex.class));
    }
}
