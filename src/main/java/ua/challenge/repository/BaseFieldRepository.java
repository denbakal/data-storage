package ua.challenge.repository;

import ua.challenge.entity.BaseField;

import java.util.List;

public interface BaseFieldRepository extends BaseRepository<BaseField, Long> {
    List<BaseField> findAllByBaseTableId(Long baseTableId);

    void delete();
}
