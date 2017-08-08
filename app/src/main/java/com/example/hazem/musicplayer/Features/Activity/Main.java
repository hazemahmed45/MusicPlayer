package com.example.hazem.musicplayer.Features.Activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Process;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazem.musicplayer.Features.Adapter.ViewPagerAdapter;
import com.example.hazem.musicplayer.Features.Fragment.AllSongsFragment;
import com.example.hazem.musicplayer.Features.Services.MusicPlayerService;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;
import com.example.hazem.musicplayer.utils.NavigationHelper;
import com.qiushui.blurredview.BlurredView;

import java.io.IOException;

public class Main extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    View MusicBar;
    ImageView SongImageView,Play,Last,First;
    TextView SongTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false))
        {
            Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
            this.finish();
            Process.killProcess( Process.myPid() );
        }
        SettingIDs();
        SettingEvents();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MusicPlayerService.mediaPlayer!=null)
        {
            MusicBar.setVisibility(View.VISIBLE);
            if(Song.GetSongs().get(MusicPlayerService.postion).getCover()==null)
            {
                SongImageView.setImageResource(R.drawable.songicon);
            }
            else
            {
                SongImageView.setImageBitmap(Song.GetSongs().get(MusicPlayerService.postion).getCover());
            }
            SongTitle.setText(Song.GetSongs().get(MusicPlayerService.postion).getDisplayName());
        }
        else
        {
            MusicBar.setVisibility(View.GONE);
        }
    }
    void SettingIDs()
    {
        viewPager= (ViewPager) findViewById(R.id.view_pager);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        adapter=new ViewPagerAdapter(this.getSupportFragmentManager());
        MusicBar=findViewById(R.id.mainscreen_include);
        SongImageView= (ImageView) findViewById(R.id.imageView2);
        SongTitle= (TextView) findViewById(R.id.mainscreen_songtitle);
        Play= (ImageView) findViewById(R.id.mainscreen_play);
        First= (ImageView) findViewById(R.id.mainscreen_first);
        Last= (ImageView) findViewById(R.id.mainscreen_last);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    void SettingEvents()
    {
        First.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerService.postion++;
                if(MusicPlayerService.postion>= Song.GetSongs().size())
                {
                    MusicPlayerService.postion=0;
                }
                SongTitle.setText(Song.GetSongs().get(MusicPlayerService.postion).getDisplayName());
                MusicPlayerService.mediaPlayer.release();
                MusicPlayerService.mediaPlayer=new MediaPlayer();
                try {

                    MusicPlayerService.mediaPlayer.setDataSource(Main.this, Uri.parse(Song.GetSongs().get(MusicPlayerService.postion).getPath()));
                    MusicPlayerService.mediaPlayer.prepare();
                    MusicPlayerService.mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicPlayerService.mediaPlayer.isPlaying()==true)
                {
                    MusicPlayerService.mediaPlayer.pause();
                    Play.setImageResource(android.R.drawable.ic_media_play);
                    SongScreen.checkPause=true;
                }
                else
                {
                    MusicPlayerService.mediaPlayer.start();
                    Play.setImageResource(android.R.drawable.ic_media_pause);
                    SongScreen.checkPause=false;
                }
            }
        });
        Last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicPlayerService.mediaPlayer.getCurrentPosition()<10000)
                {
                    MusicPlayerService.postion--;
                    if(MusicPlayerService.postion<0)
                    {
                        MusicPlayerService.postion=Song.GetSongs().size()-1;
                    }
                    SongTitle.setText(Song.GetSongs().get(MusicPlayerService.postion).getDisplayName());
                }
                MusicPlayerService.mediaPlayer.release();
                MusicPlayerService.mediaPlayer=new MediaPlayer();
                try {

                    MusicPlayerService.mediaPlayer.setDataSource(Main.this,Uri.parse(Song.GetSongs().get(MusicPlayerService.postion).getPath()));
                    MusicPlayerService.mediaPlayer.prepare();
                    MusicPlayerService.mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        SongTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHelper.LaunchToSongScreen(Main.this,Song.GetSongs().get(MusicPlayerService.postion),MusicPlayerService.postion);
            }
        });
    }
}
