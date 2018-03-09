package ua.challenge.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeoPointDto {
    private double lat;
    private double lon;
}
