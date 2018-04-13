package ua.challenge.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ua.challenge.entity.Address;
import ua.challenge.entity.Marketing;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "person_index", type = "persons")
public class PersonIndex {
    @Id
    private String id;
    private String name;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    private Date dateOfBirth;
    private String gender;
    private Integer children;

    @Field(type = FieldType.Nested)
    private Marketing marketing;

    @Field(type = FieldType.Object)
    private Address address;
}
