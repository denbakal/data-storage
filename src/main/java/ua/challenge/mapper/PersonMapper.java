package ua.challenge.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ua.challenge.dto.PersonDto;
import ua.challenge.entity.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toPerson(PersonDto personDto);

    PersonDto fromPerson(Person person);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<PersonDto> fromPersonList(List<Person> baseLaneList);
}
