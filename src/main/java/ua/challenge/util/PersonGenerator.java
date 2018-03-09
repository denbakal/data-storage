package ua.challenge.util;

import org.apache.commons.lang3.time.DateUtils;
import ua.challenge.dto.AddressDto;
import ua.challenge.dto.GeoPointDto;
import ua.challenge.dto.MarketingDto;
import ua.challenge.dto.PersonDto;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class PersonGenerator {
    public static List<String> names;

    static {
        try {
            PersonGenerator.names = CsvReader.readAsStrings("persons.csv");
        } catch (IOException e) {
            System.err.println("Can not generate names from CSV");
        }
    }

    public static PersonDto personGenerator() throws IOException {
        PersonDto person = new PersonDto();
        buildGender(person);
        person.setDateOfBirth(buildBirthDate());
        person.setMarketing(buildMeta());
        person.setAddress(buildAddress());
        person.setChildren(buildChildren());

        return person;
    }

    private static MarketingDto buildMeta() {
        MarketingDto marketing = new MarketingDto();
        int nbMeta = numberGenerator(1, 5);

        for (int i = 0; i < nbMeta; i++) {
            int nbConsult = numberGenerator(30, 2000);
            int typeMeta = numberGenerator(0, 9);
            switch (typeMeta) {
                case 0:
                    marketing.setShoes(nbConsult);
                    break;
                case 1:
                    marketing.setToys(nbConsult);
                    break;
                case 2:
                    marketing.setFashion(nbConsult);
                    break;
                case 3:
                    marketing.setMusic(nbConsult);
                    break;
                case 4:
                    marketing.setGarden(nbConsult);
                    break;
                case 5:
                    marketing.setElectronic(nbConsult);
                    break;
                case 6:
                    marketing.setHifi(nbConsult);
                    break;
                case 7:
                    marketing.setCars(nbConsult);
                    break;
                case 8:
                    marketing.setFood(nbConsult);
                    break;
                default:
                    System.err.println("   ->" + typeMeta);
                    break;
            }
        }

        return marketing;

    }

    private static Date buildBirthDate() {
        String birthDate = "" + numberGenerator(1940, 70) + "-" + numberGenerator(1, 12) + "-" + numberGenerator(1, 28);
        Date date = null;
        try {
            date = DateUtils.parseDate(birthDate, new String[]{"yyyy-MM-dd"});
        } catch (ParseException e) {
            System.err.println("buildBirthDate ->" + birthDate);
        }
        return date;
    }

    private static void buildGender(PersonDto person) throws IOException {
        int pos = numberGenerator(0, names.size());

        String line = names.get(pos);
        String[] temp = line.split("\\,");
        person.setName(temp[0] + " " + CsvReader.extractFromCommas(
                names.get(numberGenerator(0, names.size()))).get(0));
        person.setGender(temp[1]);
    }

    private static AddressDto buildAddress() throws IOException {
        AddressDto address = new AddressDto();
        generateCountry(address);
        Long result = Math.round(Math.random() * 2);

        if ("FR".equals(address.getCountryCode())) {
            switch (result.intValue()) {
                case 0:
                    address.setCity("Paris");
                    address.setZipCode("75000");
                    address.setLocation(new GeoPointDto(doubleGenerator(48.819918, 48.900552), doubleGenerator(2.25929, 2.4158559)));
                    break;
                case 1:
                    address.setCity("Nantes");
                    address.setZipCode("44000");
                    address.setLocation(new GeoPointDto(doubleGenerator(47.157742, 47.270729), doubleGenerator(-1.623467, -1.471032)));
                    break;
                case 2:
                    address.setCity("Cergy");
                    address.setZipCode("95000");
                    address.setLocation(new GeoPointDto(doubleGenerator(49.019583, 49.059419), doubleGenerator(2.003001, 2.090892)));
                    break;
                default:
                    System.err.println("buildAddress ->" + result.intValue());
                    break;
            }
        }

        if ("GB".equals(address.getCountryCode())) {
            switch (result.intValue()) {
                case 0:
                    address.setCity("London");
                    address.setZipCode("98888");
                    address.setLocation(new GeoPointDto(doubleGenerator(51.444014, 51.607633), doubleGenerator(-0.294245, 0.064184)));
                    break;
                case 1:
                    address.setCity("Plymouth");
                    address.setZipCode("5226");
                    address.setLocation(new GeoPointDto(doubleGenerator(50.345272, 50.434797), doubleGenerator(-4.190161, -4.034636)));
                    break;
                case 2:
                    address.setCity("Liverpool");
                    address.setZipCode("86767");
                    address.setLocation(new GeoPointDto(doubleGenerator(53.345346, 53.496339), doubleGenerator(-3.047485, -2.564774)));
                    break;
                default:
                    System.err.println("buildAddress ->" + result.intValue());
                    break;
            }
        }

        if ("DE".equals(address.getCountryCode())) {
            switch (result.intValue()) {
                case 0:
                    address.setCity("Berlin");
                    address.setZipCode("9998");
                    address.setLocation(new GeoPointDto(doubleGenerator(52.364796, 52.639827), doubleGenerator(13.115778, 13.769465)));
                    break;
                case 1:
                    address.setCity("Bonn");
                    address.setZipCode("0099");
                    address.setLocation(new GeoPointDto(doubleGenerator(50.649948, 50.766049), doubleGenerator(7.025075, 7.214589)));
                    break;
                case 2:
                    address.setCity("Munich");
                    address.setZipCode("45445");
                    address.setLocation(new GeoPointDto(doubleGenerator(48.081337, 48.238441), doubleGenerator(11.371548, 11.711437)));
                    break;
                default:
                    System.err.println("buildAddress ->" + result.intValue());
                    break;
            }
        }

        if ("IT".equals(address.getCountryCode())) {
            switch (result.intValue()) {
                case 0:
                    address.setCity("Rome");
                    address.setZipCode("00100");
                    address.setLocation(new GeoPointDto(doubleGenerator(41.797211, 41.980805), doubleGenerator(12.373950, 12.601393)));
                    break;
                case 1:
                    address.setCity("Turin");
                    address.setZipCode("10100");
                    address.setLocation(new GeoPointDto(doubleGenerator(45.007912, 45.122125), doubleGenerator(7.593528, 7.747337)));
                    break;
                case 2:
                    address.setCity("Ischia");
                    address.setZipCode("80100");
                    address.setLocation(new GeoPointDto(doubleGenerator(40.704982, 40.758477), doubleGenerator(13.859360, 13.953002)));
                    break;
                default:
                    System.err.println("buildAddress ->" + result.intValue());
                    break;
            }
        }

        return address;
    }

    private static String generateCountry(AddressDto address) {

        int result = numberGenerator(0,4);

        switch (result) {
            case 0:
                address.setCountry("France");
                address.setCountryCode("FR");
                break;
            case 1:
                address.setCountry("Germany");
                address.setCountryCode("DE");
                break;
            case 2:
                address.setCountry("England");
                address.setCountryCode("GB");
                break;
            case 3:
                address.setCountry("Italy");
                address.setCountryCode("IT");
                break;
            default:
                System.err.println("generateCountry ->" + result);
                break;
        }

        return null;
    }

    private static Integer buildChildren() {
        return numberGenerator(0,5);
    }

    private static int numberGenerator(int min, int range) {
        return (int) Math.floor(Math.random() * range + min);
    }

    private static double doubleGenerator(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }
}
