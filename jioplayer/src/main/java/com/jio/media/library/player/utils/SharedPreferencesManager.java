package com.jio.media.library.player.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private String prefName = "JioApplicationPreferences";
    private static SharedPreferencesManager instance;
    private SharedPreferences prefs;

    public static final String SAVED_TIME_STAMP = "SAVED_TIME_STAMP";

    private SharedPreferencesManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public synchronized static SharedPreferencesManager get(Context context)
    {
        if (instance == null)
        {
            instance = new SharedPreferencesManager(context);
        }

        return instance;
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public String getString(String key)
    {
        String value = prefs.getString(key, null);
        return value;
    }

    public long getLong(String key)
    {
        return prefs.getLong(key, 0);
    }
}
