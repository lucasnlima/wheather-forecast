package com.tech.weatherforecast.application.forecast.query;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.tech.weatherforecast.domain.Forecast;
import com.tech.weatherforecast.infrastructure.apis.nominatim.NominatimClient;
import com.tech.weatherforecast.infrastructure.apis.nominatim.model.NominatimResponse;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.OpenMeteoClient;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.OpenMeteoResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetForecastByPostalCode {

    private final NominatimClient nominatonClient;
    private final OpenMeteoClient openMeteoClient;
    private final Cache<String, Forecast> cache;

    public Forecast get(String country, String postalcode) {
        String cacheKey = country + postalcode;
        Forecast cachedForecast = cache.getIfPresent(cacheKey);
        if (cachedForecast != null) {
            cachedForecast.setFromCache();
            return cachedForecast;
        }

        NominatimResponse coordinates = nominatonClient.get(country, postalcode);
        OpenMeteoResponse weatherInfo = openMeteoClient.get(coordinates.getLat(), coordinates.getLon());

        Forecast forecast = Forecast.builder()
                .cityName(coordinates.getDisplayName())
                .temperature(weatherInfo.getTemperature())
                .maxTemperature(weatherInfo.getMaxTemperature())
                .minTemperature(weatherInfo.getMinTemperature())
                .precipitation(weatherInfo.getPrecipitation())
                .fromCache(false)
                .build();

        cache.put(cacheKey, forecast);
        return forecast;
    }

}
