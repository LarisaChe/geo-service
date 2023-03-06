package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceImplTest {
    @Test
    public void testLocationByIp_otherIP() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location locationTest = geoService.byIp("1.0.1.1");
        Assertions.assertNull(locationTest);
    }
    @ParameterizedTest
    @CsvSource({
            "Moscow, RUSSIA, Lenina, 15, 172.0.32.11",
            "New York, USA,  10th Avenue, 32, 96.44.183.149",
            "Moscow, RUSSIA,, 0, 172.23.24.25",
            ",,,0, 127.0.0.1"
    })
    public void testLocationByIp_params(String city, Country country, String street, int building, String ip) {
        Location locationExpected = new Location(city, country, street, building);

        GeoServiceImpl geoService = new GeoServiceImpl();
        Location locationTest = geoService.byIp(ip);

        Assertions.assertEquals(locationExpected.getCountry(), locationTest.getCountry());
        Assertions.assertEquals(locationExpected.getCity(), locationTest.getCity());
        if (locationTest.getStreet() != null) {
            Assertions.assertEquals(locationExpected.getStreet().trim(), locationTest.getStreet().trim());
        }
        Assertions.assertEquals(locationExpected.getBuiling(), locationTest.getBuiling());
    }
    @Test
    public void testLocationByCoordinates_throwsException() {
        final GeoServiceImpl geoService = new GeoServiceImpl();
        Assertions.assertThrows(RuntimeException.class,() ->{ geoService.byCoordinates(1, 1);
        });
    }
}
