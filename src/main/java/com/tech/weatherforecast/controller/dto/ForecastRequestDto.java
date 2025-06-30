package com.tech.weatherforecast.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastRequestDto {
    @NotBlank(message = "Country must not be blank")
    private String country;

    @NotBlank(message = "Postal code must not be blank")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Postal code must be in the format 12345-678 or 12345678")
    private String postalCode;
}