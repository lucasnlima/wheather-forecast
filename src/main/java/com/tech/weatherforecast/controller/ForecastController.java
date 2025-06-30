package com.tech.weatherforecast.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.weatherforecast.application.forecast.query.GetForecastByPostalCode;
import com.tech.weatherforecast.domain.Forecast;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/forecast")
@RequiredArgsConstructor
public class ForecastController {

    private final GetForecastByPostalCode getForecastByPostalCode;

    @GetMapping("/{country}/{postalCode}")
    public ResponseEntity<Forecast> getForecast(
            @PathVariable String country,
            @PathVariable String postalCode) {

        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country must not be blank");
        }
        if (postalCode == null || !postalCode.matches("\\d+")) {
            throw new IllegalArgumentException("Postal code must contain only digits");
        }

        Forecast forecast = getForecastByPostalCode.get(country, postalCode);
        return ResponseEntity.ok(forecast);
    }
}
