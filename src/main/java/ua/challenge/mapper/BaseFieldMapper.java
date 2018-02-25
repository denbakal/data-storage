package ua.challenge.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ua.challenge.dto.BaseFieldDto;
import ua.challenge.entity.BaseField;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseFieldMapper {
    BaseField toBaseField(BaseFieldDto baseFieldDto);

    BaseFieldDto fromBaseField(BaseField baseField);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<BaseFieldDto> fromBaseFieldkList(List<BaseField> closeRemarkList);
}
