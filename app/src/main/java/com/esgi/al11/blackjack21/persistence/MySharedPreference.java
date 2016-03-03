package com.esgi.al11.blackjack21.persistence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hussam on 03/03/2016.
 */
public class MySharedPreference  {

    public static final String PREFS_NAME = "Blackjack21";

    private Context context;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    public MySharedPreference(Context c) {
        super();
        this.context = c;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public void save(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return settings.getString(key, null);
    }

    public void clearSharedPreferences() {
        editor.clear();
        editor.apply();
    }

    public void removeValue(String key) {
        editor.remove(key);
        editor.apply();
    }

    public boolean contains(String key) {
        return settings.contains(key);
    }
}
