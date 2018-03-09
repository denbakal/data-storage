package ua.challenge.repository.impl;

import org.springframework.stereotype.Repository;
import ua.challenge.aspect.Loggable;
import ua.challenge.entity.Person;
import ua.challenge.entity.QPerson;
import ua.challenge.repository.PersonRepository;

import java.util.List;

@Repository
public class PersonRepositoryImpl extends BaseRepositoryImpl<Person, Long> implements PersonRepository {
    @Override
    @Loggable
    public List<Person> search(String name, String country, String city) {
        QPerson person = QPerson.person;
        return this.queryFactory.selectFrom(person).where(
                    person.name.like(name + "%")
                    .or(person.address.country.like(country + "%"))
                    .or(person.address.city.like(city + "%"))
        ).fetch();
    }
}
