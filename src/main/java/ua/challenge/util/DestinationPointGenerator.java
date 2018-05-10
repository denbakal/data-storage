package ua.challenge.util;

import ua.challenge.entity.elasticsearch.DestinationPoint;

import java.util.Arrays;
import java.util.List;

public class DestinationPointGenerator {
    public static DestinationPoint generate(int randomIndex) {
        DestinationPoint result = new DestinationPoint();

        List<String> points = Arrays.asList("UA", "FR", "IT");

        result.setPoint(points.get(randomIndex));
        result.setCodes(Arrays.asList("12345", "22334", "77889"));

        return result;
    }
}
