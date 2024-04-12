package com.management.vehicle.request;

import com.google.gson.Gson;
import com.management.vehicle.request.struct.GeocodingResponse;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.request.struct.RouteRequest;
import com.management.vehicle.request.struct.RouteResponse;
import com.management.vehicle.trip.Coordinate;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MapRequest {
    public static MapRequest instance;
    private String apikey;

    private MapRequest() throws Exception {
        FireBase fb = FireBase.getInstance();
        apikey = fb.getAPIKey();
    }

    public static MapRequest getInstance() throws Exception {
        if (instance == null) {
            instance = new MapRequest();
        }
        return instance;
    }

    /**
     * Retrieves the distance matrix between two sets of coordinates.
     *
     * @param fromCoordinate A list of origin coordinates.
     * @param toCoordinate   A list of destination coordinates.
     * @return A RouteMatrix object containing the distance, duration, and coordinates of the route.
     * @throws IOException If an I/O error occurs when creating the URL connection or reading from it.
     */
    public RouteMatrix getDistanceMatrix(List fromCoordinate, List toCoordinate) throws IOException {
        RouteMatrix distanceMatrix = new RouteMatrix();

        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setPoints(List.of(fromCoordinate, toCoordinate));

        Gson gson = new Gson();
        String body = gson.toJson(routeRequest);
        HttpsURLConnection urlConnection = postHttpsURLConnection("https://graphhopper.com/api/1/route?key=" + apikey, body);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        RouteResponse response = gson.fromJson(bufferedReader.readLine(), RouteResponse.class);
        List<Coordinate> coordinates = new ArrayList<>();
        for (List<Double> point : response.getPaths().get(0).getPoints().getCoordinates()) {
            coordinates.add(new Coordinate(point.get(0), point.get(1)));
        }

        distanceMatrix.setDistance(response.getPaths().get(0).getDistance());
        distanceMatrix.setDuration(response.getPaths().get(0).getTime());
        distanceMatrix.setCoordinates(coordinates);
        return distanceMatrix;
    }

    /**
     * Retrieves a list of hits (possible matches) for a given address using the GraphHopper Geocoding API.
     *
     * @param address The address to be geocoded.
     * @return A list of Hit objects, each representing a possible match for the given address.
     * @throws IOException If an I/O error occurs when creating the URL connection or reading from it.
     */
    public List<Hit> getCoordinateList(String address) throws IOException {
        HttpsURLConnection urlConnection = getHttpsURLConnection("https://graphhopper.com/api/1/geocode?q=" + URLEncoder.encode(address, StandardCharsets.UTF_8) + "&key=" + apikey);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String data;
        while ((data = bufferedReader.readLine()) != null) {
            response.append(data);
        }
        Gson gson = new Gson();
        GeocodingResponse geocodingResponse = gson.fromJson(response.toString(), GeocodingResponse.class);
        return geocodingResponse.getHits();
    }

    /**
     * Creates and returns a HttpsURLConnection object for a given URL string.
     * The connection is configured to use the GET method, and has a connection timeout of 5000 milliseconds.
     * The "Content-Type" request property is set to "application/x-www-form-urlencoded".
     *
     * @param urlString The URL string for which the connection is to be created.
     * @return A HttpsURLConnection object configured with the provided URL string.
     * @throws IOException If an I/O error occurs when creating the URL connection or attempting to connect.
     */
    private HttpsURLConnection getHttpsURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setConnectTimeout(5000);
        try {
            urlConnection.connect();
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
        return urlConnection;
    }

    /**
     * Creates and returns a HttpsURLConnection object for a given URL string.
     * The connection is configured to use the POST method, and has a connection timeout of 5000 milliseconds.
     * The provided body content is written to the connection's output stream.
     *
     * @param urlString The URL string for which the connection is to be created.
     * @param body      The body content to be written to the connection's output stream.
     * @return A HttpsURLConnection object configured with the provided URL string and body content.
     * @throws IOException If an I/O error occurs when creating the URL connection, writing to its output stream, or attempting to connect.
     */
    private HttpsURLConnection postHttpsURLConnection(String urlString, String body) throws IOException {
        URL url = new URL(urlString);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setConnectTimeout(5000);
        urlConnection.getOutputStream().write(body.getBytes());
        try {
            urlConnection.connect();
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
        return urlConnection;
    }
}