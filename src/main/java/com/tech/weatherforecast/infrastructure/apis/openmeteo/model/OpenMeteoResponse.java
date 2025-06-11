package com.tech.weatherforecast.infrastructure.apis.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMeteoResponse {

    private CurrentWeather current;
    private WeatherUnits current_units;

    public String getTemperature() {
        return String.format("%.1f %s", this.current.getTemperature_2m(), this.current_units.getTemperature_2m());
    }

    public String getMaxTemperature() {
        return String.format("%.1f %s", this.current.getTemperature_2m_max(),
                this.current_units.getTemperature_2m_max());
    }

    public String getMinTemperature() {
        return String.format("%.1f %s", this.current.getTemperature_2m_min(),
                this.current_units.getTemperature_2m_min());
    }

    public String getPrecipitation() {
        return String.format("%.1f %s (Probability: %.0f%%)",
                this.current.getPrecipitation(),
                this.current_units.getPrecipitation(),
                this.current.getPrecipitation_probability());
    }
}
