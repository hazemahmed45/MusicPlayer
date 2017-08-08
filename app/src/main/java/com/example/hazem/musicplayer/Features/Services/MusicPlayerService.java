package com.example.hazem.musicplayer.Features.Services;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazem.musicplayer.Base.Const.NavConst;
import com.example.hazem.musicplayer.Base.Const.PrefConst;
import com.example.hazem.musicplayer.Features.Activity.Main;
import com.example.hazem.musicplayer.Features.Activity.MainScreen;
import com.example.hazem.musicplayer.Features.Activity.SongScreen;
import com.example.hazem.musicplayer.Features.Activity.Splash;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.abdolahi.CircularMusicProgressBar;

/**
 * Created by Hazem on 5/24/2017.
 */

public class MusicPlayerService extends Service {

    public static MediaPlayer mediaPlayer;
    public static Song song;
    int counter=0;
    public static int postion;
    public static boolean check;
    int mStartMode;
    public static RemoteViews bigViews;
    public static RemoteViews views;
    public static Notification status;
    public static MusicPlayerService mInstance;
    IBinder mBinder=new LocalBinder();

    @Override
    public void onCreate() {

    }
    public class LocalBinder extends Binder{
        public MusicPlayerService getServiceInstance(){
            return MusicPlayerService.this;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mInstance=this;
        if (intent.getAction().equals(PrefConst.ACTION.STARTFOREGROUND_ACTION))
        {
            postion=intent.getIntExtra(NavConst.SongBundleKPos,1);
            song=Song.GetSongs().get(postion);
            PlayMusic();
            showNotification();
        }
        else if (intent.getAction().equals(PrefConst.ACTION.PREV_ACTION)&& intent.getAction()!=null)
        {
            Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            if(mediaPlayer.getCurrentPosition()<10000)
            {
                postion--;
                if(MusicPlayerService.postion<0)
                {
                    postion=Song.GetSongs().size()-1;
                }
                song=Song.GetSongs().get(postion);
                showNotification();
            }
            NextSong();
        }
        else if (intent.getAction().equals(PrefConst.ACTION.PLAY_ACTION)&& intent.getAction()!=null)
        {
            Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.pause();
                showNotification();
                SongScreen.checkPause=true;
            }
            else
            {
                MusicPlayerService.mediaPlayer.start();
                showNotification();
                SongScreen.checkPause=false;
            }
        }
        else if (intent.getAction().equals(PrefConst.ACTION.NEXT_ACTION)&& intent.getAction()!=null)
        {
            Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            postion++;
            if(postion>=Song.GetSongs().size())
            {
                postion=0;
            }
            song=Song.GetSongs().get(postion);
                showNotification();
                NextSong();
        }
        else if (intent.getAction().equals(PrefConst.ACTION.STOPFOREGROUND_ACTION)&& intent.getAction()!=null)
        {

//            SongScreen.mIsBound=true;
            SongScreen.IsServiceCheck=true;
            stopForeground(true);
            stopSelf();

        }
        return Service.START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void showNotification()
    {
        views = new RemoteViews(getPackageName(), R.layout.custom_notification);
        bigViews = new RemoteViews(getPackageName(), R.layout.custom_notification_expandable);

        Intent notificationIntent = new Intent(this, Main.class);
        notificationIntent.setAction(PrefConst.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent previousIntent = new Intent(this, MusicPlayerService.class);
        previousIntent.setAction(PrefConst.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0, previousIntent, 0);

        Intent playIntent = new Intent(this, MusicPlayerService.class);
        playIntent.setAction(PrefConst.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Intent nextIntent = new Intent(this, MusicPlayerService.class);
        nextIntent.setAction(PrefConst.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0, nextIntent, 0);

        Intent closeIntent = new Intent(this, MusicPlayerService.class);
        closeIntent.setAction(PrefConst.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0, closeIntent, 0);

        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

        bigViews.setImageViewBitmap(R.id.status_bar_album_art, PrefConst.getDefaultAlbumArt(this));

        SettingText();

        if(mediaPlayer.isPlaying())
        {
            bigViews.setImageViewResource(R.id.status_bar_play, R.drawable.apollo_holo_dark_pause);
        }
        else
        {
            bigViews.setImageViewResource(R.id.status_bar_play, R.drawable.apollo_holo_dark_play);
        }
//        bigViews.setTextViewText(R.id.status_bar_track_name, song.getDisplayName());
//
//        bigViews.setTextViewText(R.id.status_bar_artist_name, song.getArtist());
        status = new Notification.Builder(this).build();
        status.bigContentView = bigViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.contentIntent = pendingIntent;
        status.icon = android.R.drawable.ic_media_play;

        startForeground(PrefConst.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }

    private void SettingText()
    {
        views.setTextViewText(R.id.status_bar_track_name, Song.GetSongs().get(postion).getDisplayName());
        views.setTextViewText(R.id.status_bar_artist_name, Song.GetSongs().get(postion).getArtist());

        bigViews.setTextViewText(R.id.status_bar_track_name, Song.GetSongs().get(postion).getDisplayName());
        bigViews.setTextViewText(R.id.status_bar_artist_name, Song.GetSongs().get(postion).getArtist());
    }

    @Override
    public void onDestroy() {
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        String Package=componentInfo.getPackageName();
        Toast.makeText(mInstance, Package, Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();
        mediaPlayer.release();
        mediaPlayer=null;


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    private void PlayMusic()
    {

        if(check==false)
        {
            String Path=song.getPath();
            Uri uriPath=Uri.parse(Path);
            if(mediaPlayer==null)
            {
                mediaPlayer=new MediaPlayer();
            }
            else
            {
                //mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=new MediaPlayer();
            }
            try {
                //Toast.makeText(MainScreen.this, "here", Toast.LENGTH_SHORT).show();
                mediaPlayer.setDataSource(MusicPlayerService.this,uriPath);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    void NextSong()
    {
        mediaPlayer.release();
        mediaPlayer=new MediaPlayer();
        try
        {

            mediaPlayer.setDataSource(MusicPlayerService.this,Uri.parse(Song.GetSongs().get(postion).getPath()));
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
