package ua.challenge.repository;

import ua.challenge.entity.BaseFieldValue;

import java.util.List;

public interface BaseFieldValueRepository extends BaseRepository<BaseFieldValue, Long> {
    void saveValues(List<String> values);

    List<String> findFieldValuesByBaseTableId(Long id);
}
