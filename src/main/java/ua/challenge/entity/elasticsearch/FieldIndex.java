package ua.challenge.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "field_index", type = "fields")
@Setting(settingPath = "/elasticsearch/field/settings/settings.json")
public class FieldIndex {
    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long clientId;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    private Date start;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    private Date end;

    @Field(type = FieldType.Object)
    private DestinationPoint fromPoint;

    @Field(type = FieldType.Object)
    private DestinationPoint toPoint;

    @Field(type = FieldType.Long)
    private Long recordId;

    @Field(type = FieldType.String)
    @JsonProperty("FROM")
    private String from;

    @Field(type = FieldType.String)
    @JsonProperty("TO")
    private String to;

    @Field(type = FieldType.String)
    @JsonProperty("SERVICE")
    private String service;
}
