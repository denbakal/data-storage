package ua.challenge.dto;

import lombok.*;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CatalogCategory {
    @QuerySqlField
    private long id;

    @QuerySqlField
    private Long parentId;

    @QuerySqlField
    private String name;

    @QuerySqlField
    private String description;
}
