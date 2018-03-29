package ua.challenge.cache.model;

import lombok.*;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Department implements Serializable {
    @QuerySqlField(index = true)
    private Long id;

    @QuerySqlField(index = true)
    private String name;
}
