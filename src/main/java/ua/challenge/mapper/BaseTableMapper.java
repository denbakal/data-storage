package ua.challenge.mapper;

import org.mapstruct.Mapper;
import ua.challenge.dto.BaseTableDto;
import ua.challenge.entity.BaseTable;

@Mapper(componentModel = "spring")
public interface BaseTableMapper {
    BaseTable toBaseTable(BaseTableDto baseTableDto);
}