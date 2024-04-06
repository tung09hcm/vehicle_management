package com.management.vehicle.request;

import com.management.vehicle.request.struct.GeocodingResponse;
import com.management.vehicle.request.struct.Hit;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.management.vehicle.request.struct.RouteRequest;
import com.management.vehicle.request.struct.RouteResponse;
import com.management.vehicle.trip.Coordinate;

public class MapRequest {
    private String apikey;
    public MapRequest() throws Exception {
        FireBase fb = FireBase.getInstance();
        apikey = fb.getAPIKey();
        System.out.println(apikey);
    }

    public static void main(String[] args) throws Exception {
        MapRequest mapRequest = new MapRequest();
        List<Hit> hits = mapRequest.getCoordinateList("thpt dầu giây");
        for (Hit hit : hits) {
            System.out.println(hit.getName() + " " + hit.getPoint().getLat() + " " + hit.getPoint().getLng());
        }
    }

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

    private List<Hit> getCoordinateList(String address) throws IOException {
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
    private HttpsURLConnection getHttpsURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setConnectTimeout(5000);
        try {
            urlConnection.connect();
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
        return urlConnection;
    }

    private HttpsURLConnection postHttpsURLConnection(String urlString, String body) throws IOException {
        URL url = new URL(urlString);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
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

    private String regex(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            if (regex.equals("\\d+")) {
                StringBuilder resultBuilder = new StringBuilder(matcher.group());
                while (matcher.find()) {
                    resultBuilder.append(matcher.group());
                }
                return resultBuilder.toString();
            }
            return matcher.group(1);
        }
        return null;
    }

    private String secToTime(int sec) {
        int hours = sec / 3600;
        int minutes = (sec % 3600) / 60;
        int seconds = sec % 60;
        if (hours == 0) {
            return minutes + " phút " + seconds + " giây";
        }
        return hours + " giờ " + minutes + " phút " + seconds + " giây";
    }
}