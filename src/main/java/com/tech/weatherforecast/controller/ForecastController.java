package com.tech.weatherforecast.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.weatherforecast.application.forecast.query.GetForecastByPostalCode;
import com.tech.weatherforecast.domain.Forecast;
import com.tech.weatherforecast.controller.dto.ForecastRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/forecast")
@RequiredArgsConstructor
@Validated
public class ForecastController {

    private final GetForecastByPostalCode getForecastByPostalCode;

    @PostMapping
    public ResponseEntity<Forecast> getForecast(@Valid @RequestBody ForecastRequestDto request) {
        Forecast forecast = getForecastByPostalCode.get(request.getCountry(), request.getPostalCode());
        return ResponseEntity.ok(forecast);
    }
}
