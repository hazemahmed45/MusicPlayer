package com.example.hazem.musicplayer.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.hazem.musicplayer.BuildConfig;


/**
 * Created by Morabea on 4/19/16.
 */
public abstract class LoggerUtil {

    private static final String TAG = "Hotels";

    private static final boolean DEBUG = BuildConfig.DEBUG;


    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG && validInput(tag, msg))
            Log.d(tag, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG && validInput(tag, msg))
            Log.i(tag, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG && validInput(tag, msg))
            Log.e(tag, msg);
    }


    private static boolean validInput(String tag, String msg) {
        return !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg);
    }

}
