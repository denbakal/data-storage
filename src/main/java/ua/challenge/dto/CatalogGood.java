package ua.challenge.dto;

import lombok.*;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CatalogGood {
    @QuerySqlField
    private long id;

    @QuerySqlField
    private long categoryId;

    @QuerySqlField
    private String name;

    @QuerySqlField
    private String description;

    @QuerySqlField
    private long price;

    @QuerySqlField
    private long oldPrice;
}
