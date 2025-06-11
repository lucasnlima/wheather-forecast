package com.tech.weatherforecast.infrastructure.apis.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherUnits {

    private String temperature_2m;
    private String temperature_2m_max;
    private String temperature_2m_min;
    private String precipitation_sum;
    private String precipitation_probability_max;

}
