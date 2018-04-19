package ua.challenge.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.challenge.dto.PersonDto;
import ua.challenge.dto.SearchResultDto;
import ua.challenge.service.PersonService;

import java.util.List;

@Log4j2
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @PutMapping(path = "/persons")
    public void put(@RequestBody Long size) {
        log.debug("Size of persons: {}", size);
        this.personService.init(size);
    }

    @GetMapping(path = "/persons")
    public List<PersonDto> get() {
        return this.personService.getPersons();
    }

    @GetMapping(path = "/persons/advanced-search")
    public List<PersonDto> search(@RequestParam String name, @RequestParam String country, @RequestParam String city) {
        log.debug("Search by next params: {}, {}, {}", name, country, city);
        return this.personService.advancedSearch(name, country, city);
    }

    @GetMapping("/persons/search")
    public SearchResultDto search(@RequestParam String query) {
        return this.personService.search(query);
    }
}
