package ua.challenge.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ua.challenge.dto.BaseFieldValueDto;
import ua.challenge.entity.BaseFieldValue;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseFieldValueMapper {
    BaseFieldValue toBaseFieldValue(BaseFieldValueDto baseFieldDto);

    BaseFieldValueDto fromBaseFieldValue(BaseFieldValue baseField);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<BaseFieldValueDto> fromBaseFieldValueList(List<BaseFieldValue> baseFieldValueList);
}
