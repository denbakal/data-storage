package ua.challenge.repository.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.challenge.entity.BaseFieldValue;
import ua.challenge.entity.QBaseFieldValue;
import ua.challenge.repository.BaseFieldValueRepository;

import java.sql.Array;
import java.sql.CallableStatement;
import java.util.List;

@Repository
public class BaseFieldValueRepositoryImpl extends BaseRepositoryImpl<BaseFieldValue, Long> implements BaseFieldValueRepository {
    @Override
    @Transactional
    public void saveValues(List<String> values) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            Array inArray = connection.createArrayOf("text", values.toArray());

            try (CallableStatement function = connection.prepareCall("{ call store_lane_values(?) }")) {
                function.setArray(1, inArray);
                function.execute();
            }
        });
    }

    @Override
    public List<String> findFieldValuesByBaseTableId(Long id) {
        return this.entityManager.createNativeQuery("select cast(lane_value as text) from lane_value").getResultList();
    }

    @Override
    public void delete(Long id) {
        QBaseFieldValue baseFieldValue = QBaseFieldValue.baseFieldValue;
        this.queryFactory.delete(baseFieldValue).execute();
    }

    @Override
    public List<BaseFieldValue> findByBaseLaneId(Long laneId) {
        QBaseFieldValue baseFieldValue = QBaseFieldValue.baseFieldValue;

        return this.queryFactory.selectFrom(baseFieldValue)
                .where(baseFieldValue.laneId.eq(laneId)).orderBy(baseFieldValue.id.asc()).fetch();
    }
}
