package ua.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Embeddable
public class GeoPoint {
    private double lat;
    private double lon;
}
