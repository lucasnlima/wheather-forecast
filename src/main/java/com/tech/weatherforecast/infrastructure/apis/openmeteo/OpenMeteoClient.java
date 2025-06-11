package com.tech.weatherforecast.infrastructure.apis.openmeteo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.weatherforecast.infrastructure.apis.openmeteo.model.OpenMeteoResponse;

@Component
public class OpenMeteoClient {

    final static String URL = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m";

    public OpenMeteoResponse get(String lat, String lon) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL.replace("{lat}", lat).replace("{lon}", lon)))
                .build();

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            OpenMeteoResponse weatherInfo = mapper.readValue(response.body(), OpenMeteoResponse.class);
            return weatherInfo;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
