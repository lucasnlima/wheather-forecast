package com.tech.weatherforecast.infrastructure.apis.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
    private float[] temperature_2m_max;
    private float[] temperature_2m_min;
    private float[] precipitation_probability_max;
    private float[] precipitation_sum;

    public float getTemperature_2m_max() {
        return temperature_2m_max[0];
    }

    public float getTemperature_2m_min() {
        return temperature_2m_min[0];
    }

    public float getPrecipitation_probability_max() {
        return precipitation_probability_max[0];
    }

    public float getPrecipitation_sum() {
        return precipitation_sum[0];
    }
}
