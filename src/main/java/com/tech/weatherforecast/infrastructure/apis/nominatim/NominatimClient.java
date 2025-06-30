package com.tech.weatherforecast.infrastructure.apis.nominatim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.weatherforecast.domain.exception.WeatherForecastException;
import com.tech.weatherforecast.infrastructure.apis.nominatim.model.NominatimResponse;

import java.util.List;

@Component
public class NominatimClient {

    private static final String URL = "https://nominatim.openstreetmap.org/search?country={country}&postalcode={postalcode}&format=json";
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public NominatimClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public NominatimResponse getCoordinates(String country, String postalCode) {
        String url = URL.replace("{country}", country).replace("{postalcode}", postalCode);
        try {
            String responseBody = webClient.get()
                    .uri(url)
                    .header(HttpHeaders.USER_AGENT, "weather-forecast-app")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            List<NominatimResponse> coordinatesList = objectMapper.readValue(responseBody,
                    new TypeReference<List<NominatimResponse>>() {
                    });
            if (coordinatesList.isEmpty()) {
                throw new WeatherForecastException(
                        "Coordinates not found for country: " + country + " and postal code: " + postalCode);
            }
            return coordinatesList.get(0);
        } catch (WebClientResponseException e) {
            throw new WeatherForecastException(
                    "Error while trying to get coordinates from Nominatim API: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new WeatherForecastException("Unexpected error while calling Nominatim API", e);
        }
    }
}
