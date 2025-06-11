package com.tech.weatherforecast.infrastructure.apis.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMeteoResponse {

    private CurrentWeather current;
    private WeatherUnits current_units;
    private WeatherUnits daily_units;
    private DailyWeather daily;

    public String getTemperature() {
        return String.format("%.1f %s", this.current.getTemperature_2m(), this.current_units.getTemperature_2m());
    }

    public String getMaxTemperature() {
        return String.format("%.1f %s", this.daily.getTemperature_2m_max(),
                this.daily_units.getTemperature_2m_max());
    }

    public String getMinTemperature() {
        return String.format("%.1f %s", this.daily.getTemperature_2m_min(),
                this.daily_units.getTemperature_2m_min());
    }

    public String getPrecipitation() {
        return String.format("%.1f %s (Probability: %.0f%%)",
                this.daily.getPrecipitation_sum(),
                this.daily_units.getPrecipitation_sum(),
                this.daily.getPrecipitation_probability_max());
    }
}
