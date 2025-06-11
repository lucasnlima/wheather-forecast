package com.tech.weatherforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherForecastApplication {

	public static void main(String[] args) {
		System.out.println("Weather Forecast Application is running");
		SpringApplication.run(WeatherForecastApplication.class, args);
	}

}
