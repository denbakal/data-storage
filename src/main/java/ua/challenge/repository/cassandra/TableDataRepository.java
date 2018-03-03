package ua.challenge.repository.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ua.challenge.entity.cassandra.TableData;

@Repository
public interface TableDataRepository extends CassandraRepository<TableData> {
//    @Query(allowFiltering = true)
//    public List<TableData> findByValue(String value);
}
