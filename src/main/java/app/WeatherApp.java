package app;

import service.WeatherService;

import java.util.Scanner;

public class WeatherApp {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your city: ");

        String city = scanner.nextLine();

        WeatherService service = new WeatherService();
        service.getCurrentWeather(city);
    }
}
