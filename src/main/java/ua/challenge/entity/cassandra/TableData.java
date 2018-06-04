package ua.challenge.entity.cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.cassandra.core.PrimaryKeyType;
//import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
//import org.springframework.data.cassandra.mapping.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Table
public class TableData {
    /*@PrimaryKeyColumn(name = "table_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Integer id;

    @PrimaryKeyColumn(name = "version_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Integer version;

    @PrimaryKeyColumn(name = "field_name", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String fieldName;

    @PrimaryKeyColumn(name = "row_index", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private Integer rowIndex;*/

    private String value;
}