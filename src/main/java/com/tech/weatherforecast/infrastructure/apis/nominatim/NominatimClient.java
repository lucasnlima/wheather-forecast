package com.tech.weatherforecast.infrastructure.apis.nominatim;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.weatherforecast.infrastructure.apis.nominatim.model.NominatimResponse;

@Component
public class NominatimClient {

    final static String URL = "https://nominatim.openstreetmap.org/search?country={country}&postalcode={postalcode}&format=json";

    public NominatimResponse get(String country, String postalcode) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL.replace("{country}", country).replace("{postalcode}", postalcode)))
                .build();

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            List<NominatimResponse> cordinatesList = mapper.readValue(response.body(),
                    new TypeReference<List<NominatimResponse>>() {
                    });
            return cordinatesList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get weather data");
        }
    }
}
