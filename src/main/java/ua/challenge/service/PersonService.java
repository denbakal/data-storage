package ua.challenge.service;

import ua.challenge.dto.PersonDto;

import java.util.List;

public interface PersonService {
    void save(PersonDto personDto);

    void init(long size);

    List<PersonDto> getPersons();

    List<PersonDto> search(String searchText);

    List<PersonDto> advancedSearch(String name, String country, String city);
}
