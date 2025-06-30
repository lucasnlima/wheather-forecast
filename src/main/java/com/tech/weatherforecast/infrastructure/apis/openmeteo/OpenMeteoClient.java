package com.tech.weatherforecast.infrastructure.apis.openmeteo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.tech.weatherforecast.domain.exception.WeatherForecastException;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.OpenMeteoResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class OpenMeteoClient {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&daily=temperature_2m_max,temperature_2m_min,precipitation_probability_max,precipitation_sum&current=temperature_2m&start_date={start_date}&end_date={end_date}";

    private final WebClient webClient;

    @Autowired
    public OpenMeteoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public OpenMeteoResponse getForecast(String latitude, String longitude) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String url = BASE_URL
                .replace("{lat}", latitude)
                .replace("{lon}", longitude)
                .replace("{start_date}", currentDate)
                .replace("{end_date}", currentDate);
        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(OpenMeteoResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new WeatherForecastException(
                    "Error while trying to get forecast from OpenMeteo API: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new WeatherForecastException("Unexpected error while calling OpenMeteo API", e);
        }
    }
}
