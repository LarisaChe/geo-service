package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;

public class LocalizationServiceImplTest {
    @Test
    public void testLocalizationServiceRU() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        Assertions.assertEquals("Добро пожаловать", localizationService.locale(Country.RUSSIA));
    }
    @ParameterizedTest
    @ValueSource(strings = {"GERMANY", "BRAZIL", "USA"})
    public void testLocalizationServiceEN(Country country) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        Assertions.assertEquals("Welcome", localizationService.locale(country));
    }
}
