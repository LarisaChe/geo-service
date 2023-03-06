package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {
    public Map<String, String> ipaddresses = new HashMap<String, String>();
    {
        ipaddresses.put("1", "1.1.1.1");
        ipaddresses.put("2", "2.1.1.1");
        ipaddresses.put("4", "96.44.183.149");
        ipaddresses.put("5", "172.0.32.11");
        ipaddresses.put("x-real-ip", "");
        ipaddresses.put("3", "3.1.1.1");
    }

    @ParameterizedTest
    @CsvSource({
            "Moscow, RUSSIA, Lenina, 15, 172.0.32.11, Добро пожаловать",
            "New York, USA,  10th Avenue, 32, 96.44.183.149, Welcome",
            "Moscow, RUSSIA,, 0, 172.23.24.25, Добро пожаловать",
            "New York, USA,, 0, 96.0.1.4, Welcome"
    })
    public void testMessageSenderSend(String city, Country country, String street, int building, String ip, String expectedStr) {

        ipaddresses.replace("x-real-ip", ip);
        Location locateTest = new Location(city, country, street, building);

        GeoService geoServiceMock = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoServiceMock.byIp(ip)).thenReturn(locateTest);

        LocalizationService localizationServiceMock = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationServiceMock.locale(country)).thenReturn(expectedStr);

        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        String testStr = messageSender.send(ipaddresses);

        Assertions.assertEquals(expectedStr, testStr);
    }
}
