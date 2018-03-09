package ua.challenge.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonDto {
    private Long id;
    private String name = null;
    private Date dateOfBirth = null;
    private String gender = null;
    private Integer children;
    private MarketingDto marketing;
    private AddressDto address;
}
