package com.example.hazem.musicplayer.utils;

import android.graphics.drawable.Drawable;

import com.example.hazem.musicplayer.Base.MusicPlayerApplication;

/**
 * Created by mohamedelkhateeb on 1/16/17.
 */

public class DrawableUtil {

    public static Drawable getProperDrawable(int position) {
        Drawable drawable = MusicPlayerApplication.GetInstance().getResources().getDrawable(MusicPlayerApplication.GetInstance().getResources()
                .getIdentifier("cat_" + position, "drawable", MusicPlayerApplication.GetInstance().getPackageName()));
        return drawable;
    }

}
