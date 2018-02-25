package ua.challenge.repository.impl;

import org.springframework.stereotype.Repository;
import ua.challenge.entity.BaseLaneValue;
import ua.challenge.entity.QBaseLaneValue;
import ua.challenge.repository.BaseLaneValueRepository;

@Repository
public class BaseLaneValueRepositoryImpl extends BaseRepositoryImpl<BaseLaneValue, Long> implements BaseLaneValueRepository {
    @Override
    public void delete(Long id) {
        QBaseLaneValue baseLaneValue = QBaseLaneValue.baseLaneValue;
        this.queryFactory.delete(baseLaneValue).execute();
    }
}
