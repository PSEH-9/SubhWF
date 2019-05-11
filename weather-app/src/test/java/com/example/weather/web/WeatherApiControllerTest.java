package com.example.weather.web;

import com.example.weather.controller.WeatherApiController;
import com.example.weather.integration.weatherService.WeatherEntry;
import com.example.weather.integration.weatherService.WeatherForecast;
import com.example.weather.integration.weatherService.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherApiController.class)
public class WeatherApiControllerTest {

	@MockBean
	private WeatherService weatherService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void weatherForecast() throws Exception {
		WeatherForecast forecast = new WeatherForecast();
		forecast.setName("Indore");
		forecast.getEntries().add(createWeatherEntry(285.45, 600, "02d", Instant.ofEpochSecond(1234)));
		forecast.getEntries().add(createWeatherEntry(294.45, 800, "01d", Instant.ofEpochSecond(5678)));
		given(this.weatherService.getWeatherForecast("In", "Indore")).willReturn(forecast);
		this.mvc.perform(get("/api/weather/weekly/In/Indore"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Indore")))
				.andExpect(jsonPath("$.entries[0].temperature", is(285.45)))
				.andExpect(jsonPath("$.entries[1].temperature", is(294.45)));
		verify(this.weatherService).getWeatherForecast("In", "Indore");
	}

	private static WeatherEntry createWeatherEntry(double temperature, int id, String icon,
                                                   Instant timestamp) {
		WeatherEntry entry = new WeatherEntry();
		setWeatherEntry(entry, temperature, id, icon, timestamp);
		return entry;
	}

	private static void setWeatherEntry(WeatherEntry entry, double temperature, int id, String icon,
			Instant timestamp) {
		entry.setTemperature(temperature);
		entry.setWeatherId(id);
		entry.setWeatherIcon(icon);
		entry.setTimestamp(timestamp.getEpochSecond());
	}

}
