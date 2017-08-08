package com.example.hazem.musicplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.hazem.musicplayer.Base.MusicPlayerApplication;


public class PrefsUtil {

    private static final String DEFAULT_APP_PREFS_NAME = "hotels-default-prefs";

    private static SharedPreferences getPrefs(String prefsName) {
        if (TextUtils.isEmpty(prefsName))
            prefsName = DEFAULT_APP_PREFS_NAME;
        return MusicPlayerApplication.GetInstance().getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    public static void saveString(String key, String value) {
        saveString(null, key, value);
    }

    public static void saveString(String prefsName, String key, String value) {
        getPrefs(prefsName).edit().putString(key, value).apply();
    }

    public static @Nullable
    String getString(String key) {
        return getString(null, key);
    }

    public static @Nullable
    String getString(String prefsName, String key) {
        return getPrefs(prefsName).getString(key, null);
    }

    public static void saveBoolean(String key, boolean value) {
        saveBoolean(null, key, value);
    }

    public static void saveBoolean(String prefsName, String key, boolean value) {
        getPrefs(prefsName).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(null, key);
    }
    public static boolean getBoolean(String key,boolean defValue) {
        return getPrefs(null).getBoolean(key, defValue);
    }

    public static boolean getBoolean(String prefsName, String key) {
        return getPrefs(prefsName).getBoolean(key, false);
    }

    public static void clear() {
        getPrefs(null).edit().clear().commit();
    }
}
