package com.example.weather.integration.ows;

import com.example.weather.integration.weatherService.Weather;
import com.example.weather.integration.weatherService.WeatherService;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
@TestPropertySource(properties = "app.weather.api.key=test-ABC")
public class WeatherServiceTest {

	private static final String URL = "http://api.openweathermap.org/data/2.5/";

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void getWeatherForecast() {
		this.server.expect(
				requestTo(URL + "forecast?q=Indore,In&APPID=d2929e9483efc82c82c32ee7e02d563e"))
				.andRespond(withSuccess(
						new ClassPathResource("forecast-indore.json", getClass()),
						MediaType.APPLICATION_JSON));
		this.weatherService.getWeatherForecast("In", "Indore");
		this.server.verify();
	}

}
