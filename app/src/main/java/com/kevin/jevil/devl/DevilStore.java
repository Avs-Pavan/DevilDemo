package com.kevin.jevil.devl;

import android.content.Context;
import android.content.SharedPreferences;

import com.kevin.jevil.devl.models.DevilConfig;

import org.json.JSONArray;
import org.json.JSONException;

import io.reactivex.rxjava3.annotations.NonNull;

public class DevilStore {

    private static DevilConfig sConfig;
    private static String SHARED_KEY = "DEVIL_STORE";
    private static String SHARED_DB_KEY = "SHARED_DB_KEY";


    private static SharedPreferences preferences;

    public static void build(@NonNull DevilConfig devilConfig) {
        sConfig = devilConfig;
        preferences = sConfig.getmContext().getSharedPreferences(SHARED_DB_KEY, Context.MODE_PRIVATE);
    }


    public static void storeScreams(String message, String topic) {
        String string = message + "----" + topic;
        JSONArray array = retrieveJSONArray(SHARED_KEY, new JSONArray());
        array.put(string);
        storeItem(SHARED_KEY, array);

    }

    public static JSONArray getPendingScreams() {
        return retrieveJSONArray(SHARED_KEY, new JSONArray());
    }


    private static void storeItem(String key, JSONArray value) {
        storeItem(key, value.toString());
    }

    private static JSONArray retrieveJSONArray(String key, JSONArray defaultValue) {
        if (preferences != null)
            if (preferences.contains(key)) {
                try {
                    return new JSONArray(retrieveString(key, defaultValue.toString()));
                } catch (JSONException e) {
                    return defaultValue;
                }
            }
        return defaultValue;
    }

    private static void storeItem(String key, String value) {
        preferences.edit().putString(key, value).apply();

    }

    private  static String retrieveString(@NonNull String key, @NonNull String defaultValue) {
        if (preferences != null)
            if (preferences.contains(key))
                return preferences.getString(key, defaultValue);
        return defaultValue;
    }

    public static void goBlank() {
        preferences.edit().clear().apply();
    }


}
