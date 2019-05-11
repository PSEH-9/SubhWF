package com.example.weather.controller;

import com.example.weather.integration.weatherService.WeatherForecast;
import com.example.weather.integration.weatherService.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@Api(tags = "Weather", value = "WeatherForcast", description = "Weather Forcast for city")
public class WeatherApiController {

	@Autowired
	public WeatherService weatherService;

	@GetMapping("/weekly/{country}/{city}")
	@ApiOperation(value = "Provide weather forecast details")
	public WeatherForecast getWeatherForecast(@PathVariable String country,
			@PathVariable String city) throws Exception {
		return this.weatherService.getWeatherForecast(country, city);
	}

}
