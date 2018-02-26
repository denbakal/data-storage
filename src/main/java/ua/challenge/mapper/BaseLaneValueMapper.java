package ua.challenge.mapper;

import org.mapstruct.Mapper;
import ua.challenge.dto.BaseLaneValueDto;
import ua.challenge.entity.BaseLaneValue;

@Mapper(componentModel = "spring")
public interface BaseLaneValueMapper {
    BaseLaneValue toBaseLaneValue(BaseLaneValueDto baseLaneValueDto);

    BaseLaneValueDto fromBaseLane(BaseLaneValue baseFieldValue);
}
