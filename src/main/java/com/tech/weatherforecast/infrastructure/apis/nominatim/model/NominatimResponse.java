package com.tech.weatherforecast.infrastructure.apis.nominatim.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimResponse {

    private String lat;
    private String lon;
    private String display_name;

    public String getDisplayName() {
        return display_name;
    }
}
