package com.tech.weatherforecast.infrastructure.apis.nominatim.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimResponse {

    private String lat;
    private String lon;
    @JsonProperty("display_name")
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
