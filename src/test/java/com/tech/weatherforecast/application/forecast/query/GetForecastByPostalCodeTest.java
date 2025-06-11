package com.tech.weatherforecast.application.forecast.query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.benmanes.caffeine.cache.Cache;
import com.tech.weatherforecast.domain.Forecast;
import com.tech.weatherforecast.infrastructure.apis.nominatim.NominatimClient;
import com.tech.weatherforecast.infrastructure.apis.nominatim.model.NominatimResponse;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.OpenMeteoClient;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.CurrentWeather;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.DailyWeather;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.OpenMeteoResponse;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.WeatherUnits;

@ExtendWith(MockitoExtension.class)
class GetForecastByPostalCodeTest {

    @Mock
    private NominatimClient nominatimClient;

    @Mock
    private OpenMeteoClient openMeteoClient;

    @Mock
    private Cache<String, Forecast> cache;

    private GetForecastByPostalCode getForecastByPostalCode;

    @BeforeEach
    void setUp() {
        getForecastByPostalCode = new GetForecastByPostalCode(nominatimClient, openMeteoClient, cache);
    }

    @Test
    void shouldReturnCachedForecastWhenAvailable() {
        // Arrange
        String country = "Brazil";
        String postalCode = "12345";
        String cacheKey = country + postalCode;
        Forecast cachedForecast = Forecast.builder()
                .cityName("São Paulo")
                .temperature("25.0 °C")
                .maxTemperature("28.0 °C")
                .minTemperature("22.0 °C")
                .precipitation("0.0 mm (Probability: 10%)")
                .fromCache(false)
                .build();

        when(cache.getIfPresent(cacheKey)).thenReturn(cachedForecast);

        // Act
        Forecast result = getForecastByPostalCode.get(country, postalCode);

        // Assert
        assertTrue(result.isFromCache());
        assertEquals(cachedForecast.getCityName(), result.getCityName());
        assertEquals(cachedForecast.getTemperature(), result.getTemperature());
        verify(nominatimClient, never()).get(anyString(), anyString());
        verify(openMeteoClient, never()).get(anyString(), anyString());
    }

    @Test
    void shouldFetchNewForecastWhenNotInCache() {
        // Arrange
        String country = "Brazil";
        String postalCode = "12345";
        String cacheKey = country + postalCode;

        NominatimResponse nominatimResponse = new NominatimResponse();
        nominatimResponse.setLat("-23.5505");
        nominatimResponse.setLon("-46.6333");
        nominatimResponse.setDisplay_name("São Paulo, Brazil");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setTemperature_2m(25.0f);
        DailyWeather dailyWeather = new DailyWeather();
        dailyWeather.setTemperature_2m_max(new float[] { 28.0f });
        dailyWeather.setTemperature_2m_min(new float[] { 22.0f });
        dailyWeather.setPrecipitation_sum(new float[] { 0.0f });
        dailyWeather.setPrecipitation_probability_max(new float[] { 10.0f });

        WeatherUnits currentWeatherUnits = new WeatherUnits();
        currentWeatherUnits.setTemperature_2m("°C");
        WeatherUnits dailyWeatherUnits = new WeatherUnits();
        dailyWeatherUnits.setTemperature_2m_max("°C");
        dailyWeatherUnits.setTemperature_2m_min("°C");
        dailyWeatherUnits.setPrecipitation_sum("mm");
        dailyWeatherUnits.setPrecipitation_probability_max("%");

        OpenMeteoResponse openMeteoResponse = new OpenMeteoResponse();
        openMeteoResponse.setCurrent(currentWeather);
        openMeteoResponse.setDaily(dailyWeather);
        openMeteoResponse.setCurrent_units(currentWeatherUnits);
        openMeteoResponse.setDaily_units(dailyWeatherUnits);

        when(cache.getIfPresent(cacheKey)).thenReturn(null);
        when(nominatimClient.get(country, postalCode)).thenReturn(nominatimResponse);
        when(openMeteoClient.get(nominatimResponse.getLat(), nominatimResponse.getLon()))
                .thenReturn(openMeteoResponse);

        // Act
        Forecast result = getForecastByPostalCode.get(country, postalCode);

        // Assert
        assertFalse(result.isFromCache());
        assertEquals("São Paulo, Brazil", result.getCityName());
        assertEquals("25.0 °C", result.getTemperature());
        assertEquals("28.0 °C", result.getMaxTemperature());
        assertEquals("22.0 °C", result.getMinTemperature());
        assertEquals("0.0 mm (Probability: 10%)", result.getPrecipitation());
        verify(cache).put(eq(cacheKey), any(Forecast.class));
    }

    @Test
    void shouldThrowExceptionWhenNominatimClientFails() {
        // Arrange
        String country = "Brazil";
        String postalCode = "12345";
        when(cache.getIfPresent(anyString())).thenReturn(null);
        when(nominatimClient.get(country, postalCode)).thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> getForecastByPostalCode.get(country, postalCode));
        verify(openMeteoClient, never()).get(anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenOpenMeteoClientFails() {
        // Arrange
        String country = "Brazil";
        String postalCode = "12345";
        NominatimResponse nominatimResponse = new NominatimResponse();
        nominatimResponse.setLat("-23.5505");
        nominatimResponse.setLon("-46.6333");

        when(cache.getIfPresent(anyString())).thenReturn(null);
        when(nominatimClient.get(country, postalCode)).thenReturn(nominatimResponse);
        when(openMeteoClient.get(anyString(), anyString())).thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> getForecastByPostalCode.get(country, postalCode));
    }
}