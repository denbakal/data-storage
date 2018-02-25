package ua.challenge.repository.impl;

import org.springframework.stereotype.Repository;
import ua.challenge.entity.BaseField;
import ua.challenge.entity.QBaseField;
import ua.challenge.repository.BaseFieldRepository;

import java.util.List;

@Repository
public class BaseFieldRepositoryImpl extends BaseRepositoryImpl<BaseField, Long> implements BaseFieldRepository {
    @Override
    public List<BaseField> findAllByBaseTableId(Long baseTableId) {
        QBaseField baseField = QBaseField.baseField;

        return queryFactory.selectFrom(baseField)
                            .where(baseField.baseTableId.eq(baseTableId))
                            .orderBy(baseField.id.asc())
                            .fetch();
    }

    @Override
    public void delete() {
        QBaseField baseField = QBaseField.baseField;
        this.queryFactory.delete(baseField).execute();
    }
}
