package com.tech.weatherforecast.infrastructure.apis.openmeteo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.weatherforecast.domain.exception.WeatherForecastException;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.OpenMeteoResponse;

@Component
public class OpenMeteoClient {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&daily=temperature_2m_max,temperature_2m_min,precipitation_probability_max,precipitation_sum&current=temperature_2m&start_date={start_date}&end_date={end_date}";

    public OpenMeteoResponse get(String lat, String lon) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String url = BASE_URL
                .replace("{lat}", lat)
                .replace("{lon}", lon)
                .replace("{start_date}", currentDate)
                .replace("{end_date}", currentDate);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            OpenMeteoResponse weatherInfo = mapper.readValue(response.body(), OpenMeteoResponse.class);
            return weatherInfo;
        } catch (Exception e) {
            throw new WeatherForecastException("Error while trying to get forecast: " + e.getMessage(), e);
        }
    }
}
