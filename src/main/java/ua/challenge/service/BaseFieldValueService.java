package ua.challenge.service;

import ua.challenge.type.ColumnInsertType;

import java.util.List;
import java.util.Map;

public interface BaseFieldValueService {
    void storeData(List<String> values);

    void storeJsonData(List<String> values);

    void storeColumnData(List<Map<String, String>> data, ColumnInsertType type);

    void storeKeyValueData(List<String> data);

    void removeColumnData();

    List<String> getValues(Long id);

    void removeData(Long id);

    void storeCellData(List<String> values);

    void removeCellData(Long id);

    List<String> getCellValues(Long id);
}
