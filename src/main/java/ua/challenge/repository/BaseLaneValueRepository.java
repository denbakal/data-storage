package ua.challenge.repository;

import ua.challenge.entity.BaseLaneValue;

public interface BaseLaneValueRepository extends BaseRepository<BaseLaneValue, Long> {
    void delete(Long id);
}
