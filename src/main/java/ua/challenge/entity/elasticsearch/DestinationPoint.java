package ua.challenge.entity.elasticsearch;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DestinationPoint {
    private String point;
    private List<String> codes;
}
