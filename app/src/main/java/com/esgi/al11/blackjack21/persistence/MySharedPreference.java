package com.esgi.al11.blackjack21.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.esgi.al11.blackjack21.metier.Carte;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return settings.getString(key, null);
    }

    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key) {
        return settings.getInt(key, 0);
    }

    public void saveCards(String key, List<Carte> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(key, json);
        editor.apply();
    }

    public List<Carte> getCards(String key) {
        Gson gson = new Gson();
        List<Carte> cards = new ArrayList<Carte>();
        String jsonCards = settings.getString(key, null);

        Type type = new TypeToken<List<Carte>>() {}.getType();
        cards = gson.fromJson(jsonCards, type);

        return cards;
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
