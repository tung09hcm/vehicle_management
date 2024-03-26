package Request;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapRequest {

    public static void main(String[] args) throws IOException {
        MapRequest mapRequest = new MapRequest();
        DistanceMatrix response = mapRequest.getDistancematrix("Trường THPT Long Khánh", "kí túc xá khu a");
        if (response == null) {
            System.out.println("Không tìm thấy đường đi");
            return;
        }
        System.out.println(response.getOrigin_addresses());
        System.out.println(response.getDestination_addresses());
        System.out.println(response.getDistance());
        System.out.println(response.getDuration());
    }
    public DistanceMatrix getDistancematrix(String origin, String destination) throws IOException {
        HttpsURLConnection urlConnection = getHttpsURLConnection("https://api.distancematrix.ai//maps/api/distancematrix/json?origins=" + URLEncoder.encode(origin, StandardCharsets.UTF_8) + "&destinations=" + URLEncoder.encode(destination, StandardCharsets.UTF_8) + "&key=M0ioJlnrcQDFRJj55TzUAnndyWkwwEsLyUnHZ5DpzPIvq3qb2befP8Hr1nytAZpH");
        DistanceMatrix responseBody;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }
        System.out.println(response);
        if (response.indexOf("ZERO_RESULTS") != -1) return null;
        responseBody = new DistanceMatrix();
        responseBody.setOrigin_addresses(regex("\\\"origin_addresses\\\":\\[\\\"(.*?)\\\"\\]",response.toString()));
        responseBody.setDestination_addresses(regex("\\\"destination_addresses\\\":\\[\\\"(.*?)\\\"\\]",response.toString()));
        responseBody.setDistance(regex("\\\"distance\\\":\\{\\\"text\\\":\\\"(.*?)\\\",\\\"value\\\":\\d+\\}",response.toString()));
        responseBody.setDuration(secToTime(Integer.parseInt(Objects.requireNonNull(regex("\"duration\":\\{\"text\":\"[^\"]+\",\"value\":(\\d+)", response.toString())))));
        return responseBody;
    }
    private HttpsURLConnection getHttpsURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setConnectTimeout(5000);
        urlConnection.connect();
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