package service;

import api.GeoCodingClient;
import api.OpenMeteoClient;
import model.WeatherData;

public class WeatherService {

    private final GeoCodingClient geoCodingClient;
    private final OpenMeteoClient openMeteoClient;

    public WeatherService(){
        geoCodingClient = new GeoCodingClient();
        openMeteoClient = new OpenMeteoClient();
    }


    public void getCurrentWeather(String city){
        double[] coordinates = geoCodingClient.getCoordinates(city);
        WeatherData weatherData = openMeteoClient.getCurrentWeather(coordinates[0], coordinates[1]);
        System.out.println("Current weather in " + city);
        System.out.println("Temperature: " + weatherData.temperature() + "°C");
        System.out.println("Wind-speed: " + weatherData.windspeed() + " km/h");
    }
}
