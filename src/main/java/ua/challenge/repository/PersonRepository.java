package ua.challenge.repository;

import ua.challenge.entity.Person;

import java.util.List;

public interface PersonRepository extends BaseRepository<Person, Long> {
    List<Person> search(String name, String country, String city);
}
