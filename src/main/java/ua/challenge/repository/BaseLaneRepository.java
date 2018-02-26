package ua.challenge.repository;

import ua.challenge.entity.BaseLane;

import java.util.List;

public interface BaseLaneRepository extends BaseRepository<BaseLane, Long> {
    void delete(Long id);

    List<BaseLane> findByBaseTableId(Long id);
}
