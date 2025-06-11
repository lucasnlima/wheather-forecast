package com.tech.weatherforecast.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Forecast {
    private String cityName;
    private String temperature;
    private String maxTemperature;
    private String minTemperature;
    private String precipitation;
    private boolean fromCache;

    public Forecast(String cityName, String temperature, String maxTemperature, String minTemperature,
            String precipitation, boolean fromCache) {
        if (temperature == null || temperature.trim().isEmpty()) {
            throw new IllegalArgumentException("Temperature cannot be null or empty");
        }

        this.cityName = cityName;
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.precipitation = precipitation;
        this.fromCache = fromCache;
    }

    public void setFromCache() {
        this.fromCache = true;
    }
}
