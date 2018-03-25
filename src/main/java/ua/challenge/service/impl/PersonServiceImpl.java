package ua.challenge.service.impl;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.dto.PersonDto;
import ua.challenge.mapper.PersonMapper;
import ua.challenge.repository.PersonRepository;
import ua.challenge.service.PersonService;
import ua.challenge.util.PersonGenerator;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    private AtomicInteger currentItem = new AtomicInteger();

    @Override
    public void save(PersonDto personDto) {

    }

    @Override
    @SneakyThrows
    @Transactional
    public void init(long size) {
        long start = 0;
        currentItem.set(0);

        log.debug("Initializing database for {} persons", size);

        PersonDto joe = PersonGenerator.personGenerator();
        joe.setName("Joe Smith");
        joe.getAddress().setCountry("France");
        joe.getAddress().setCity("Paris");
        this.personRepository.save(this.personMapper.toPerson(joe));
        currentItem.incrementAndGet();

        PersonDto franceGall = PersonGenerator.personGenerator();
        franceGall.setName("France Gall");
        franceGall.setGender("female");
        franceGall.getAddress().setCountry("Italy");
        franceGall.getAddress().setCity("Ischia");
        this.personRepository.save(this.personMapper.toPerson(franceGall));
        currentItem.incrementAndGet();

        for (int i = 2; i < size; i++) {
            PersonDto person = PersonGenerator.personGenerator();
            this.personRepository.save(this.personMapper.toPerson(person));
            currentItem.incrementAndGet();
        }

        long took = System.currentTimeMillis() - start;

        log.debug("Database initialized with {} persons. Took: {} ms, around {} per second.",
                size, took, 1000 * size / took);
    }

    @Override
    public List<PersonDto> getPersons() {
        return this.personMapper.fromPersonList(this.personRepository.findAll());
    }

    @Override
    public List<PersonDto> search(String name, String country, String city) {
        return this.personMapper.fromPersonList(this.personRepository.search(name, country, city));
    }
}