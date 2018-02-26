package ua.challenge.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ua.challenge.dto.BaseLaneDto;
import ua.challenge.entity.BaseLane;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseLaneMapper {
    BaseLane toBaseLane(BaseLaneDto baseFieldDto);

    BaseLaneDto fromBaseLane(BaseLane baseField);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<BaseLaneDto> fromBaseLaneList(List<BaseLane> baseLaneList);
}
