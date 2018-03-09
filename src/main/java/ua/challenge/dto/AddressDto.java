package ua.challenge.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDto {
    private Integer id;
    private String country;
    private String zipCode;
    private String city;
    private String countryCode;
    private GeoPointDto location;
}
