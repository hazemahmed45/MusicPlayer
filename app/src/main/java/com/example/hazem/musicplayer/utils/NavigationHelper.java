package com.example.hazem.musicplayer.utils;

import android.app.Activity;
import android.content.Intent;


import com.example.hazem.musicplayer.Base.Const.NavConst;
import com.example.hazem.musicplayer.Base.Const.PrefConst;
import com.example.hazem.musicplayer.Features.Activity.MainScreen;
import com.example.hazem.musicplayer.Features.Activity.SongScreen;
import com.example.hazem.musicplayer.Features.Activity.Main;
import com.example.hazem.musicplayer.Features.Services.MusicPlayerService;
import com.example.hazem.musicplayer.Models.Song;

/**
 * Created by ahmed on 13/04/17.
 */

public class NavigationHelper {
    public static void LaunchToMainScreen(Activity activity){
        activity.startActivity(new Intent(activity,MainScreen.class));
    }
    public static void LaunchToMain(Activity activity){
        activity.startActivity(new Intent(activity,Main.class));
    }
    public static void LaunchToSongScreen(Activity activity,Song s,int postion){
        Intent intent=new Intent(activity,SongScreen.class);
        //intent.putExtra(NavConst.SongBundleKey,s);
        intent.putExtra(NavConst.SongBundleKPos,postion);
        activity.startActivity(intent);
    }
    public static void LaunchMusicService(Activity activity,int pos)
    {
        Intent intent=new Intent(activity,MusicPlayerService.class);
        intent.putExtra(NavConst.SongBundleKPos,pos);
        intent.setAction(PrefConst.ACTION.STARTFOREGROUND_ACTION);
        activity.startService(intent);
    }

//    public static void LaunchFragment(final Fragment fragment, FragmentManager fragmentManager, int ResLayout, Bundle bundle)
//    {
//        FragmentTransaction FT=fragmentManager.beginTransaction();
//        FT.replace(ResLayout,fragment);
//        fragment.setArguments(bundle);
////        if(fragment.getClass()== RoomScreenFragment.class)
////        {
////            FT.addToBackStack(null);
////        }
//        FT.commit();
//    }
}
