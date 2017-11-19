package com.example.android.bookList;

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

import static com.example.android.bookList.BookActivity.LOG_TAG;

public final class QueryUtils {

    private QueryUtils() {
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<com.example.android.bookList.Book> extractFeatureFromJson(String jsonResponse) {

        // Create an empty ArrayList
        List<com.example.android.bookList.Book> books = new ArrayList<>();

        // Try to parse the JSON_RESPONSE.
        try {
                JSONObject jsonObj = new JSONObject(jsonResponse);
                JSONArray features = jsonObj.getJSONArray("items");

                //Loop through each feature in the array
                for (int i = 0; i < features.length(); i++) {
                    //Get features JSONObject at position i
                    JSONObject currentBook = features.getJSONObject(i);

                    JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                    String thumbnail = null;

                    if (volumeInfo.isNull("imageLinks") == false) {
                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        //extract thumbnail
                        thumbnail = imageLinks.getString("smallThumbnail");
                    }

                    //Extract title
                    String title = volumeInfo.getString("title");

                    //Extract author
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    String[] authors = new String[authorsArray.length()];
                    for (int x = 0; x < authorsArray.length(); x++) {
                        authors[x] = authorsArray.getString(x);
                    }

                    //Extract publisher
                    String publisher = null;
                    if (volumeInfo.isNull("publisher") == false) {
                        publisher = volumeInfo.getString("publisher");
                    }

                    //Extract publishedDate
                    String publishedDate = volumeInfo.getString("publishedDate");

                    //Extract “infoLink” for link
                    String url = volumeInfo.getString("infoLink");


                    //Create Book java object from magnitude, location, and time
                    com.example.android.bookList.Book book = new com.example.android.bookList.Book(thumbnail,title,authors,publisher,publishedDate,url);
                    books.add(book);
                }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        // Return the list of books
        return books;
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
                Log.e(LOG_TAG, "url: " + url);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
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

    /**
     * Query google books
     */
    public static List<com.example.android.bookList.Book> fetchBookData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<com.example.android.bookList.Book> books = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Book}s
        return books;
    }

    /**
    * Returns new URL object from the given string URL.
    */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Problem building the URL ", e);
            }
        return url;
    }

}