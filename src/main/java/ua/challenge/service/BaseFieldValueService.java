package ua.challenge.service;

import java.util.List;

public interface BaseFieldValueService {
    void storeData(List<String> values);

    List<String> getValues(Long id);

    void removeData(Long id);
}
