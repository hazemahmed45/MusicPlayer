package com.example.hazem.musicplayer.Base;

import android.app.Application;

/**
 * Created by Hazem on 5/19/2017.
 */

public class MusicPlayerApplication extends Application {
    private static MusicPlayerApplication mInstance;
    public static MusicPlayerApplication GetInstance()
    {
        if (mInstance == null)
            // This should never happen, but just in case
            throw new IllegalStateException("Application instance has not been initialized!");
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance=this;

    }
}
