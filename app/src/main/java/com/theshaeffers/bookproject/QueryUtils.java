package com.theshaeffers.bookproject;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.theshaeffers.bookproject.MainActivity.LOG_TAG;

/**
 * Created by shaefferm on 11/4/2016.
 */

public final class QueryUtils {

    private QueryUtils() {
    }

    /**
     * Query the Google Books API and return a list of {@link Volume} objects.
     */
    public static List<Volume> fetchVolumeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Volumes}s
        List<Volume> volumes = extractVolumeFromJson(jsonResponse);

        // Return the list of {@link Volumes}
        return volumes;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the volume JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Volume> extractVolumeFromJson(String volumeJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(volumeJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding volumes to
        List<Volume> volumes = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(volumeJSON);

            //Extract the JSONArray associated with the key items; a list of volumes
            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");

            //for each volume, create a Volume object
            for (int i = 0; i < itemsArray.length(); i++) {

                //create a JSON object @ the location of the array
                JSONObject currentItem = itemsArray.getJSONObject(i);

                //create a JSON object of the volumeInfo
                JSONObject volumeInfo = currentItem.getJSONObject("volumeInfo");

                //Extract the title
                String volumeTitle = volumeInfo.getString("title");

                //Extract the url link
                String url = volumeInfo.getString("infoLink");

                String volumeAuthors = "";
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                for (int a = 0; a < authorsArray.length(); a++){
                    volumeAuthors = volumeAuthors + authorsArray.getString(a) + ", ";
                }

                // Create a new {@link Volume} object with the image, title, author, description,
                // and url from the JSON response.
                Volume volume = new Volume(volumeTitle, volumeAuthors, url);

                // Add the new {@link Volume} to the list of volumes.
                volumes.add(volume);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return volumes;

    }
}

