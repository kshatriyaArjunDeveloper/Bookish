package com.example.bookish;


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

/**
 * Helper methods related to requesting and receiving book data from Google Book api.
 */

public class QueryUtils {


    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**...................CONSTRUCTOR..HERE.......................................................*/
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /**...................METHODS..HERE.......................................................*/


    /**
     * Query the Google book API and return a list of {@link Book} objects.
     */
    public static ArrayList<Book> fetchBookData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of books
        ArrayList<Book> books = extractBooksFromJson(jsonResponse);

        // Return the list of Books
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
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
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
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> extractBooksFromJson(String book_JSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(book_JSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding books to
        ArrayList<Book> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject Json_root = new JSONObject(book_JSON);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of book items.
            JSONArray items_Array = Json_root.getJSONArray("items");

            // For each book items in the itemsArray, create an object
            for (int i = 0; i < items_Array.length(); i++) {

                Log.e("ITEMS ARRAY PROBLEM",""+items_Array.length());
                // Get a single book at position i within the list of book items
                JSONObject currentBook = items_Array.getJSONObject(i);

                // Extract the value for the key called "id"
                String id = currentBook.getString("id");
                Log.e("ITEMS ARRAY PROBLEM","BOOK");

                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which represents title,subtitle,authors
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                // Extract the value for the key called "title"
                String title = volumeInfo.getString("title").trim();
                Log.e("ITEMS ARRAY PROBLEM","TITLE");

                String subtitle ;
                if (volumeInfo.has("subtitle")){
                    // Extract the value for the key called "subtitle"
                    subtitle = volumeInfo.getString("subtitle");
                    Log.e("ITEMS ARRAY PROBLEM","SUBTITLE");
                }else {
                    subtitle = "Not Available";
                }

                // Extract the JSONArray associated with the key called "authors",
                // which represents a list of book authors.
                String authors = "";
                if (volumeInfo.has("authors")){

                    JSONArray authors_Array = volumeInfo.getJSONArray("authors");
                    // For each book authors in the authors_Array
                    for (int j = 0; j < authors_Array.length(); j++) {

                        // Extract the value for the key called "authors"
                        if (j!=0){
                            authors += " & " + authors_Array.getString(j).trim();
                            Log.e("ITEMS ARRAY PROBLEM","AUTHORS");
                        }else {
                            authors += authors_Array.getString(j).trim();
                            Log.e("ITEMS ARRAY PROBLEM","AUTHORS");
                        }

                    }

                }else {
                    authors = "Not Available";
                }

                String publisher;
                // Extract the value for the key called "publisher"
                if (volumeInfo.has("publisher")){
                    publisher = volumeInfo.getString("publisher").trim();
                    Log.e("ITEMS ARRAY PROBLEM","PUBLISHERS");
                }else {
                    publisher = "NOT AVAILABLE";
                    Log.e("ITEMS ARRAY PROBLEM","AUTHORS");
                }


                int pageCount ;
                if (volumeInfo.has("pageCount")){
                    // Extract the value for the key called "pageCount"
                    pageCount = volumeInfo.getInt("pageCount");
                    Log.e("ITEMS ARRAY PROBLEM","PAGE NO.");
                }else {
                    pageCount = 0;
                }

                // Create a new Book object with the properties.
                Book book = new Book(id,title,subtitle,authors,publisher,pageCount);

                // Add the new Book to the list of earthquakes.
                books.add(book);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of books
        return books;
    }
}
