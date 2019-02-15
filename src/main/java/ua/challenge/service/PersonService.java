package ua.challenge.service;

import ua.challenge.dto.PersonDto;
import ua.challenge.dto.SearchResultDto;

import java.io.IOException;
import java.util.List;

public interface PersonService {
    void save(PersonDto personDto);

    void init(long size) throws Throwable;

    List<PersonDto> getPersons();

    SearchResultDto search(String searchText);

    List<PersonDto> advancedSearch(String name, String country, String city);
}
