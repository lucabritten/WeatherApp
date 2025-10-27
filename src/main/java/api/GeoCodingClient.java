package api;

import com.google.gson.Gson;
import model.GeocodingResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GeoCodingClient {

    private final String BASE_URL = "https://geocoding-api.open-meteo.com/v1/search?name=";
    private final HttpClient client;
    private final Gson gson;

    public GeoCodingClient(){
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public double[] getCoordinates(String city){
        try {
            String request_url = BASE_URL + URLEncoder.encode(city, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(request_url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            GeocodingResponse geocode = gson.fromJson(response.body(), GeocodingResponse.class);

            if(geocode.results() == null || geocode.results().isEmpty())
                throw new RuntimeException("No result for city: " + city);

            var firstResult = geocode.results().getFirst();
            return new double[]{firstResult.latitude(), firstResult.longitude()};

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while requesting coordinates for city: " + city, e);
        }
    }
}
