package ua.challenge.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ua.challenge.dto.PersonDto;
import ua.challenge.entity.elasticsearch.PersonIndex;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonIndexMapper {
    PersonIndex toPersonIndex(PersonDto personDto);

    PersonDto fromPersonIndex(PersonIndex person);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<PersonDto> fromPersonIndexList(List<PersonIndex> personIndexList);
}
