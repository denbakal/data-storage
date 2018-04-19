package ua.challenge.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BucketDto {
    private String key;
    private Long count;
}
