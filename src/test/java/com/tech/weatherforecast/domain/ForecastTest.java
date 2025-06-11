package com.tech.weatherforecast.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ForecastTest {

    @Test
    void shouldCreateValidForecast() {
        // Arrange
        String cityName = "São Paulo";
        String temperature = "25.0 °C";
        String maxTemperature = "28.0 °C";
        String minTemperature = "22.0 °C";
        String precipitation = "0.0 mm (Probability: 10%)";

        // Act
        Forecast forecast = Forecast.builder()
                .cityName(cityName)
                .temperature(temperature)
                .maxTemperature(maxTemperature)
                .minTemperature(minTemperature)
                .precipitation(precipitation)
                .fromCache(false)
                .build();

        // Assert
        assertEquals(cityName, forecast.getCityName());
        assertEquals(temperature, forecast.getTemperature());
        assertEquals(maxTemperature, forecast.getMaxTemperature());
        assertEquals(minTemperature, forecast.getMinTemperature());
        assertEquals(precipitation, forecast.getPrecipitation());
        assertFalse(forecast.isFromCache());
    }

    @Test
    void shouldThrowExceptionWhenTemperatureIsNull() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Forecast.builder()
                .cityName("São Paulo")
                .temperature(null)
                .maxTemperature("28.0 °C")
                .minTemperature("22.0 °C")
                .precipitation("0.0 mm")
                .fromCache(false)
                .build());
    }

    @Test
    void shouldThrowExceptionWhenTemperatureIsEmpty() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Forecast.builder()
                .cityName("São Paulo")
                .temperature("")
                .maxTemperature("28.0 °C")
                .minTemperature("22.0 °C")
                .precipitation("0.0 mm")
                .fromCache(false)
                .build());
    }

    @Test
    void shouldSetFromCache() {
        // Arrange
        Forecast forecast = Forecast.builder()
                .cityName("São Paulo")
                .temperature("25.0 °C")
                .maxTemperature("28.0 °C")
                .minTemperature("22.0 °C")
                .precipitation("0.0 mm")
                .fromCache(false)
                .build();

        // Act
        forecast.setFromCache();

        // Assert
        assertTrue(forecast.isFromCache());
    }
}