package com.example.hazem.musicplayer.Features.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazem.musicplayer.Base.Const.NavConst;
import com.example.hazem.musicplayer.Base.MusicPlayerApplication;
import com.example.hazem.musicplayer.Features.Adapter.SongAdapter;
import com.example.hazem.musicplayer.Features.Services.MusicPlayerService;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;
import com.example.hazem.musicplayer.utils.NavigationHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainScreen extends AppCompatActivity {

    ListView listview;
    SongAdapter adapter;
    ImageView First,Play,Last;
    MaterialSearchBar searchView;
    TextView SongTitle;
    ImageView SongImageView;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false))
        {
            Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
            this.finish();
            Process.killProcess( Process.myPid() );
        }
        settingIDs();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();

        adapter=new SongAdapter(Song.GeTemptSongs(),this);
        listview.setAdapter(adapter);


        View MusicBar=findViewById(R.id.include);
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
    void settingIDs()
    {
        listview= (ListView) findViewById(R.id.song_list_view);
        Last= (ImageView) findViewById(R.id.mainscreen_last);
        First= (ImageView) findViewById(R.id.mainscreen_first);
        Play= (ImageView) findViewById(R.id.mainscreen_play);
        SongTitle= (TextView) findViewById(R.id.mainscreen_songtitle);
        searchView= (MaterialSearchBar) findViewById(R.id.song_searchview);
        SongImageView= (ImageView) findViewById(R.id.imageView2);

        settingEvents();

    }
    void settingEvents()
    {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Song s=adapter.getItem(i);
                int pos=Song.GeTemptSongs().get(i).second;
                NavigationHelper.LaunchToSongScreen(MainScreen.this,s,pos);

            }
        });
        searchView.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                Toast.makeText(MainScreen.this, enabled+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Song.NewTempArrayList();
                for (int i=0;i<Song.GetSongs().size();i++)
                {
                    if(Song.GetSongs().get(i).getDisplayName().contains(text.toString()))
                    {
                        Song.GeTemptSongs().add(new Pair<Song, Integer>(Song.GetSongs().get(i),i));

                    }
                }
                adapter=new SongAdapter(Song.GeTemptSongs(),MainScreen.this);
                listview.setAdapter(adapter);
                searchView.enableSearch();

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        searchView.setHint("Search");
        searchView.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i3, int i1, int i2) {
                Song.NewTempArrayList();
                for (int i=0;i<Song.GetSongs().size();i++)
                {
                    if(Song.GetSongs().get(i).getDisplayName().contains(charSequence.toString()))
                    {
                        Song.GeTemptSongs().add(new Pair<Song, Integer>(Song.GetSongs().get(i),i));

                    }
                }
                adapter=new SongAdapter(Song.GeTemptSongs(),MainScreen.this);
                listview.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        First.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerService.postion++;
                if(MusicPlayerService.postion>=Song.GetSongs().size())
                {
                    MusicPlayerService.postion=0;
                }
                SongTitle.setText(Song.GetSongs().get(MusicPlayerService.postion).getDisplayName());
                MusicPlayerService.mediaPlayer.release();
                MusicPlayerService.mediaPlayer=new MediaPlayer();
                try {

                    MusicPlayerService.mediaPlayer.setDataSource(MainScreen.this,Uri.parse(Song.GetSongs().get(MusicPlayerService.postion).getPath()));
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

                    MusicPlayerService.mediaPlayer.setDataSource(MainScreen.this,Uri.parse(Song.GetSongs().get(MusicPlayerService.postion).getPath()));
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
                NavigationHelper.LaunchToSongScreen(MainScreen.this,Song.GetSongs().get(MusicPlayerService.postion),MusicPlayerService.postion);
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menufile,menu);
//        //MenuItem item=menu.findItem(R.id.action_search);
//        //searchView.setMenuItem(item);
//        searchView= (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        SearchManager searchManger= (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManger.getSearchableInfo(getComponentName()));
//        return true;
//    }
}
