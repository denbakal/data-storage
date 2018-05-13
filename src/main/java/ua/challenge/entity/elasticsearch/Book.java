package ua.challenge.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "test-index", type = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS")
    private Date releaseDate;

    @Field(type = FieldType.Nested)
    private Publisher publisher;
}
