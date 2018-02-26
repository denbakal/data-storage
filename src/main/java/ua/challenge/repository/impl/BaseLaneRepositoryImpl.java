package ua.challenge.repository.impl;

import org.springframework.stereotype.Repository;
import ua.challenge.entity.BaseLane;
import ua.challenge.entity.QBaseLane;
import ua.challenge.repository.BaseLaneRepository;

import java.util.List;

@Repository
public class BaseLaneRepositoryImpl extends BaseRepositoryImpl<BaseLane, Long> implements BaseLaneRepository {
    @Override
    public void delete(Long id) {
        QBaseLane baseLane = QBaseLane.baseLane;
        this.queryFactory.delete(baseLane).execute();
    }

    @Override
    public List<BaseLane> findByBaseTableId(Long id) {
        QBaseLane baseLane = QBaseLane.baseLane;

        return this.queryFactory.selectFrom(baseLane)
                .where(
                        baseLane.baseTableId.eq(id)
                )
                .orderBy(baseLane.ordinal.asc())
                .fetch();
    }
}
