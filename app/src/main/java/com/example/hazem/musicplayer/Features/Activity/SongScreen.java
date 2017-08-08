package com.example.hazem.musicplayer.Features.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazem.musicplayer.Base.Const.NavConst;
import com.example.hazem.musicplayer.Base.Const.PrefConst;
import com.example.hazem.musicplayer.Base.MusicPlayerApplication;
import com.example.hazem.musicplayer.Features.Services.MusicPlayerService;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;
import com.qiushui.blurredview.BlurredView;

import java.io.IOException;

import info.abdolahi.CircularMusicProgressBar;


public class SongScreen extends AppCompatActivity {
    public static boolean mIsBound ;
    private TextView SongTitle,SongArtist,StartTime,EndTime;
    private ImageButton Play,First,Last,Rewind,FastForward;
    private SeekBar SongSeekBar;
    private Handler myHandler=new Handler();
    private final int Forward=5000,Backward=5000;
    private Song song;
    public static boolean checkPause=false;
    private CircularMusicProgressBar musicProgressBar;
    Intent intent;
    BlurredView blurredView;
    public static boolean IsServiceCheck=false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_screen);
        SetIDs();
        SetEvents();
        intent=new Intent(this,MusicPlayerService.class);
        intent.putExtra(NavConst.SongBundleKPos,getIntent().getIntExtra(NavConst.SongBundleKPos,0));
        intent.setAction(PrefConst.ACTION.STARTFOREGROUND_ACTION);
        startService(intent);
        mIsBound=false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        int pos=getIntent().getIntExtra(NavConst.SongBundleKPos,0);
        if(pos!=MusicPlayerService.postion || MusicPlayerService.postion==0)
        {
            MusicPlayerService.check=false;
            MusicPlayerService.postion=pos;
        }
        else
        {
            MusicPlayerService.check=true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        IsServiceCheck=false;
//        song=MusicPlayerService.song;
//        settingText();
//
//        //Toast.makeText(this, "se", Toast.LENGTH_SHORT).show();
//

        myHandler.postDelayed(UpdateSongTime,100);
        BlurredView blurredView= (BlurredView) findViewById(R.id.blurredview);
        blurredView.setBlurredLevel(100);
        blurredView.setBlurredTop(50);
//        if(MusicPlayerService.song==null)
//        {
//            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
//        }
//        else
//        Toast.makeText(this, MusicPlayerService.song.getDisplayName()+"", Toast.LENGTH_SHORT).show();

    }

    private void SetIDs()
    {
        SongTitle= (TextView) findViewById(R.id.songscreen_title);
        SongArtist= (TextView) findViewById(R.id.songscreen_artist);
        StartTime= (TextView) findViewById(R.id.start_time);
        EndTime= (TextView) findViewById(R.id.end_time);
        Play= (ImageButton) findViewById(R.id.Play);
        First= (ImageButton) findViewById(R.id.First);
        Last= (ImageButton) findViewById(R.id.Last);
        Rewind= (ImageButton) findViewById(R.id.rewind);
        FastForward= (ImageButton) findViewById(R.id.fastforward);
        SongSeekBar= (SeekBar) findViewById(R.id.song_seekbar);
        musicProgressBar= (CircularMusicProgressBar) findViewById(R.id.song_image);
        blurredView= (BlurredView) findViewById(R.id.blurredview);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void settingText()
    {

        song=Song.GetSongs().get(MusicPlayerService.postion);
        //Toast.makeText(this, song.getDisplayName()+" "+song.getDuration(), Toast.LENGTH_SHORT).show();
        SongTitle.setText(song.getDisplayName());
        SongArtist.setText(song.getArtist());
        EndTime.setText(SetMinsAndSecs((int)song.getDuration()));
        StartTime.setText(SetMinsAndSecs(0));
        SongSeekBar.setMax((int) song.getDuration());

        musicProgressBar.setProgressAnimatorInterpolator(new LinearInterpolator());
        musicProgressBar.setBorderColor(getResources().getColor(R.color.grey));
        musicProgressBar.setBorderProgressColor(getResources().getColor(R.color.teal));
        musicProgressBar.setImageBitmap(song.getCover());
        blurredView.setBlurredImg(song.getCover());




    }
//    public void NotificationFunction()
//    {
//        int NotificationID=1;
//        NotificationCompat.Builder notif=new NotificationCompat.Builder(this);
//        notif.setContentText(song.getDisplayName());
//        notif.setContentTitle("Playing...");
//        notif.setSmallIcon(R.drawable.ic_stat_song);
//        Intent intent=new Intent(this,MainScreen.class);
//        PendingIntent pendingIntent=PendingIntent.getActivity(SongScreen.this,1,intent,PendingIntent.FLAG_ONE_SHOT);
//        notif.addAction(new android.support.v4.app.NotificationCompat.Action(R.drawable.cd,"intent",pendingIntent));
//
//        //notif.getNotification().flags= Notification.PRIORITY_HIGH|Notification.FLAG_ONGOING_EVENT|Notification.FLAG_NO_CLEAR;
//        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(NotificationID,notif.build());
//
//    }

    private void SetEvents()
    {
        SongSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                {
                    MusicPlayerService.mediaPlayer.seekTo(i);
                    SongSeekBar.setProgress(i);
                    StartTime.setText(SetMinsAndSecs(i));

                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicPlayerService.mediaPlayer!=null)
                {
                    if(MusicPlayerService.mediaPlayer.isPlaying())
                    {
                        MusicPlayerService.mediaPlayer.pause();
                        Play.setImageResource(android.R.drawable.ic_media_play);
                        checkPause=true;
                        MusicPlayerService.mInstance.showNotification();
                    }
                    else
                    {
                        MusicPlayerService.mediaPlayer.start();
                        Play.setImageResource(android.R.drawable.ic_media_pause);
                        checkPause=false;
                        MusicPlayerService.mInstance.showNotification();
                    }
                }

            }
        });
        First.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(MusicPlayerService.mediaPlayer.getCurrentPosition()<10000)
                {
                    MusicPlayerService.postion--;
                    if(MusicPlayerService.postion<0)
                    {
                        MusicPlayerService.postion=Song.GetSongs().size()-1;
                    }
                    settingText();
                }
                NextSong();
                MusicPlayerService.mInstance.showNotification();

            }
        });
        Last.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                MusicPlayerService.postion++;
                if(MusicPlayerService.postion>=Song.GetSongs().size())
                {
                    MusicPlayerService.postion=0;
                }
                settingText();
                NextSong();
                MusicPlayerService.mInstance.showNotification();
            }
        });
        FastForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(song.getDuration()<=MusicPlayerService.mediaPlayer.getCurrentPosition()+Forward)
                {
                    Toast.makeText(SongScreen.this, "you can't fast forward", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MusicPlayerService.mediaPlayer.seekTo(MusicPlayerService.mediaPlayer.getCurrentPosition()+Forward);
                }
            }
        });
        Rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicPlayerService.mediaPlayer.getCurrentPosition()-Backward<=0)
                {
                    Toast.makeText(SongScreen.this, "you can't fast forward", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MusicPlayerService.mediaPlayer.seekTo(MusicPlayerService.mediaPlayer.getCurrentPosition()-Backward);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IsServiceCheck=true;
    }

    void NextSong()
    {
        MusicPlayerService.mediaPlayer.release();
        MusicPlayerService.mediaPlayer=new MediaPlayer();
        try {

            MusicPlayerService.mediaPlayer.setDataSource(SongScreen.this,Uri.parse(Song.GetSongs().get(MusicPlayerService.postion).getPath()));
            MusicPlayerService.mediaPlayer.prepare();
            MusicPlayerService.mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String SetMinsAndSecs(int Duration)
    {
        return String.format("%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(Duration),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(Duration)-
                        java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(Duration)));
    }


    private Runnable UpdateSongTime=new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            myHandler.removeCallbacks(UpdateSongTime);
            if(!IsServiceCheck)
            {
                if(!MusicPlayerService.song.equals(song) || MusicPlayerService.song==null)
                {
                    MusicPlayerService.song=Song.GetSongs().get(MusicPlayerService.postion);
                    song=MusicPlayerService.song;
                    settingText();
                    SongSeekBar.setMax((int) song.getDuration());
                    MusicPlayerService.mInstance.showNotification();
                }
                if(!SongTitle.getText().equals(MusicPlayerService.song.getDisplayName()))
                {
                    settingText();
                    SongSeekBar.setMax((int) song.getDuration());
                    MusicPlayerService.mInstance.showNotification();
                }

                SongSeekBar.setProgress(MusicPlayerService.mediaPlayer.getCurrentPosition());
                StartTime.setText(SetMinsAndSecs(MusicPlayerService.mediaPlayer.getCurrentPosition()));
                musicProgressBar.setValue((MusicPlayerService.mediaPlayer.getCurrentPosition()*100)/MusicPlayerService.mediaPlayer.getDuration());

                if(!MusicPlayerService.mediaPlayer.isPlaying() && !checkPause)
                {
                    MusicPlayerService.postion++;
                    if(MusicPlayerService.postion>=Song.GetSongs().size())
                    {
                        MusicPlayerService.postion=0;
                    }
                    settingText();
                    NextSong();
                }
                if(!MusicPlayerService.mediaPlayer.isPlaying())
                {
                    Play.setImageResource(android.R.drawable.ic_media_play);
                }
                else
                {
                    Play.setImageResource(android.R.drawable.ic_media_pause);
                }
                myHandler.postDelayed(this,100);
            }
            else
            {

                if(mIsBound)
                {
                    Intent intent=new Intent(SongScreen.this,Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT",true);
                    startActivity(intent);
                    mIsBound=false;
                }

            }
        }
    };

}
