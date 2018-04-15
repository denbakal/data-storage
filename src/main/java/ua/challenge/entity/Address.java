package ua.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String gender;

    @MultiField(
            mainField = @Field(type = FieldType.String),
            otherFields = {
                    @InnerField(suffix = "autocomplete", type = FieldType.String, indexAnalyzer = "ngram", searchAnalyzer = "simple")
            }
    )
    private String country;
    private String zipCode;

    @MultiField(
            mainField = @Field(type = FieldType.String),
            otherFields = {
                    @InnerField(suffix = "autocomplete", type = FieldType.String, indexAnalyzer = "ngram", searchAnalyzer = "simple")
            }
    )
    private String city;
    private String countryCode;
    @Embedded
    private GeoPoint location;
}
