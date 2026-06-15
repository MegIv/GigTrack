package com.sisteminformasi.gigtrack;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePreferences {
    private static final String PREF_NAME = "gigtrack_prefs";
    private static final String KEY_IS_DARK_MODE = "is_dark_mode";
    private final SharedPreferences sharedPreferences;

    public ThemePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setDarkMode(boolean isDarkMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_DARK_MODE, isDarkMode);
        editor.apply();
    }

    public boolean isDarkMode() {
        return sharedPreferences.getBoolean(KEY_IS_DARK_MODE, false);
    }
}
