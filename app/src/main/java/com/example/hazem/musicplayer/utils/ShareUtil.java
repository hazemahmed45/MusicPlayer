package com.example.hazem.musicplayer.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Ahmed on 2/1/2017.
 */

public class ShareUtil {

    public static Intent getBrowserIntent(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }
}
