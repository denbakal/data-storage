package ua.challenge.cache.model;

import lombok.*;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee implements Serializable {
    @QuerySqlField(index = true)
    private Long id;

    @QuerySqlField(index = true)
    private String firstName;

    @QuerySqlField(index = true)
    private String lastName;

    @QuerySqlField(index = true)
    private Date birthday;

    @QuerySqlField(index = true)
    private Long departmentId;

}
