package ua.challenge.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import ua.challenge.entity.Address;
import ua.challenge.entity.Marketing;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "person_index", type = "persons")
@Setting(settingPath = "/elasticsearch/person/settings/settings.json")
public class PersonIndex {
    @Id
    private String id;

//    @Field(type = FieldType.String, analyzer = "ngram")
//    @Field(type = FieldType.String, analyzer = "ngram", searchAnalyzer = "simple")
    @MultiField(
            mainField = @Field(type = FieldType.String),
            otherFields = {
                    @InnerField(suffix = "autocomplete", type = FieldType.String, indexAnalyzer = "ngram", searchAnalyzer = "simple")
            }
    )
    private String name;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    private Date dateOfBirth;

    @MultiField(
            mainField = @Field(type = FieldType.String),
            otherFields = {
                    @InnerField(suffix = "autocomplete", type = FieldType.String, indexAnalyzer = "ngram", searchAnalyzer = "simple")
            }
    )
    private String gender;
    private Integer children;

    @Field(type = FieldType.Nested)
    private Marketing marketing;

    @Field(type = FieldType.Object)
    private Address address;
}
