package com.example.hazem.musicplayer.utils;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.hazem.musicplayer.Base.MusicPlayerApplication;

/**
 * Created by Hazem on 4/16/2017.
 */

public class ColorUtil {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getColor(int Id)
    {
        return MusicPlayerApplication.GetInstance().getColor(Id);
    }
}
