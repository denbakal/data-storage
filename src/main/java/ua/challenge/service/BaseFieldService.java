package ua.challenge.service;

import ua.challenge.dto.BaseFieldDto;
import ua.challenge.entity.BaseField;

import java.util.List;

public interface BaseFieldService {
    List<BaseFieldDto> getFieldsByBaseTableId(Long baseTableId);
    void generate(int countFields);
    void delete();
}
