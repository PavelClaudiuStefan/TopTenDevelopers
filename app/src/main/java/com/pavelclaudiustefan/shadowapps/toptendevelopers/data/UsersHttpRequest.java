package com.pavelclaudiustefan.shadowapps.toptendevelopers.data;

import android.text.TextUtils;
import android.util.Log;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.models.User;

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
 * Created by Claudiu on 24-Mar-18.
 */

public class UsersHttpRequest {

    private final static String LOG_TAG = "UsersHttpRequest";

    public static ArrayList<User> getUsers(String stringUrl) {
        URL url = createUrl(stringUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractUsersFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error while building url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

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

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
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

    private static ArrayList<User> extractUsersFromJson(String usersJson) {
        if (TextUtils.isEmpty(usersJson)) {
            return null;
        }

        ArrayList<User> users = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(usersJson);
            JSONArray usersArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject currentUser = usersArray.getJSONObject(i);

                String name = currentUser.getString("display_name");
                String location = currentUser.getString("location");
                String profileImageUrl = currentUser.getString("profile_image");
                JSONObject badgesObject = currentUser.getJSONObject("badge_counts");
                int goldBadges = badgesObject.getInt("gold");
                int silverBadges = badgesObject.getInt("silver");
                int bronzeBadges = badgesObject.getInt("bronze");

                User user = new User(name, profileImageUrl, location);
                user.setBadgesCount(goldBadges, silverBadges, bronzeBadges);

                users.add(user);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing json results", e);
        }

        return users;
    }

}
