package ua.challenge.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MarketingDto {
    private Integer id;
    private Integer cars;
    private Integer shoes;
    private Integer toys;
    private Integer fashion;
    private Integer music;
    private Integer garden;
    private Integer electronic;
    private Integer hifi;
    private Integer food;
}
