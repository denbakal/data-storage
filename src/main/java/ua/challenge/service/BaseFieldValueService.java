package ua.challenge.service;

import java.util.List;

public interface BaseFieldValueService {
    void storeData(List<String> values);

    void storeJsonData(List<String> values);

    List<String> getValues(Long id);

    void removeData(Long id);

    void storeCellData(List<String> values);

    void removeCellData(Long id);

    List<String> getCellValues(Long id);
}
