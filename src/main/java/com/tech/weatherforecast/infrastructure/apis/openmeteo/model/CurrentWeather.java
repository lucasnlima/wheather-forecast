package com.tech.weatherforecast.infrastructure.apis.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
    private float temperature_2m;
    private float temperature_2m_max;
    private float temperature_2m_min;
    private float precipitation;
    private float precipitation_probability;
}
