package ua.challenge.service;

import ua.challenge.dto.PersonDto;
import ua.challenge.dto.SearchResultDto;

import java.util.List;

public interface PersonService {
    void save(PersonDto personDto);

    void init(long size);

    List<PersonDto> getPersons();

    SearchResultDto search(String searchText);

    List<PersonDto> advancedSearch(String name, String country, String city);
}
