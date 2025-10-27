package api;

import com.google.gson.Gson;
import model.WeatherData;
import model.WeatherResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoClient {

    private final HttpClient httpClient;
    private final Gson gson;

    public OpenMeteoClient(){
        httpClient = HttpClient.newHttpClient();
        gson = new Gson();
    }

    public WeatherData getCurrentWeather(Double latitude, Double longitude){

        String weatherUrl = String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true",
                latitude, longitude);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(weatherUrl))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            WeatherResponse weatherResponse = gson.fromJson(response.body(), WeatherResponse.class);

            var currentWeather = weatherResponse.current_weather();
            return new WeatherData(longitude, latitude, currentWeather.temperature(), currentWeather.windspeed());
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while requesting weather data. ", e);
        }
    }
}
