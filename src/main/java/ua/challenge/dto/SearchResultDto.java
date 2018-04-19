package ua.challenge.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchResultDto {
    private List<PersonDto> persons;
    private List<BucketDto> countries;
    private List<BucketDto> years;
}
