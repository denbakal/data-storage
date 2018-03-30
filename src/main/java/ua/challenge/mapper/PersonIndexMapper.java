package ua.challenge.mapper;

import org.mapstruct.Mapper;
import ua.challenge.dto.PersonDto;
import ua.challenge.entity.elasticsearch.PersonIndex;

@Mapper(componentModel = "spring")
public interface PersonIndexMapper {
    PersonIndex toPersonIndex(PersonDto personDto);

    PersonDto fromPersonIndex(PersonIndex person);
}
