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

    @GetMapping("/{country}/{postalcode}")
    public ResponseEntity<Forecast> getForecast(@PathVariable String country, @PathVariable String postalcode) {
        Forecast forecast = getForecastByPostalCode.get(country, postalcode);
        return ResponseEntity.ok(forecast);
    }
}
