package com.example.hazem.musicplayer.Features.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hazem.musicplayer.Base.MusicPlayerApplication;
import com.example.hazem.musicplayer.Models.Album;
import com.example.hazem.musicplayer.Models.Artist;
import com.example.hazem.musicplayer.Models.Folder;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;
import com.example.hazem.musicplayer.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.Map;

public class Splash extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
            {

            }
            requestPermissions(new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else
        {
            if(Song.GetSongs().size()<=0)
            {
                //GetArtists();
                GetAlbums();
                GetSongs();
                Song.getSongsCover();
                Album.MakeAlbums();
                for (Map.Entry<String,ArrayList<Song>> album:Album.getAlbumsAndSongs().entrySet())
                {
                    Log.i("ALBUM",album.getValue().size()+"");
                }
                Folder.GetFolders(Song.GetSongs());
            }
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    NavigationHelper.LaunchToMain(Splash.this);
                    finish();
                }
            },2000);

        }

    }
    void GetAlbums()
    {
        ContentResolver MusicResolver=getContentResolver();
        Uri MusicUri= MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor MusicCursor=MusicResolver.query(MusicUri,null,null,null,null);
        if(MusicCursor!=null && MusicCursor.moveToNext()) {
            int NameColumn = MusicCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM);
            int ArtColumn = MusicCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
            int ArtistColumn = MusicCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST);
            do {

                String Name = MusicCursor.getString(NameColumn);
                String Art = MusicCursor.getString(ArtColumn);
                String Artist = MusicCursor.getString(ArtistColumn);
                //int ID=MusicCursor.getInt(IdColumn);

                Bitmap cover = BitmapFactory.decodeFile(Art);
                if(cover==null)
                {
                    cover=BitmapFactory.decodeResource(MusicPlayerApplication.GetInstance().getResources(),R.drawable.songicon);
                }
                Album album = new Album(Name, Artist, cover);
                Album.getAlbumsMap().put(Name, album);
                //Log.i("ALBUMS", Name);
            } while (MusicCursor.moveToNext());

        }
    }


//    void GetArtists()
//    {
//        ContentResolver MusicResolver=getContentResolver();
//        Uri MusicUri= MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
//        Cursor MusicCursor=MusicResolver.query(MusicUri,null,null,null,null);
//        if(MusicCursor!=null && MusicCursor.moveToNext())
//        {
//            int NameColumn=MusicCursor.getColumnIndex(MediaStore.Audio.ArtistColumns.ARTIST);
//            do {
//                String Name=MusicCursor.getString(NameColumn);
//
//                Artist artist=new Artist(Name);
//                Artist.getArtists().add(artist);
//                Log.i("AAAAAAAAAA",Name);
//            }while(MusicCursor.moveToNext());
//        }
//    }
//    void GetGenres()
//    {
//        ContentResolver MusicResolver=getContentResolver();
//        Uri MusicUri= MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
//        Cursor MusicCursor=MusicResolver.query(MusicUri,null,null,null,null);
//        if(MusicCursor!=null && MusicCursor.moveToNext())
//        {
//            int NameColumn=MusicCursor.getColumnIndex(MediaStore.Audio.GenresColumns.NAME);
//
//
//            do {
//                String Name=MusicCursor.getString(NameColumn);
//
//                Log.i("llllll",Name);
//            }while(MusicCursor.moveToNext());
//        }
//    }
//    void GetPlayLists()
//    {
//        ContentResolver MusicResolver=getContentResolver();
//        Uri MusicUri= MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
//        Cursor MusicCursor=MusicResolver.query(MusicUri,null,null,null,null);
//        //MediaStore.Audio.Playlists.Members.
//    }

    void GetSongs()
    {
        ContentResolver MusicResolver=getContentResolver();
        Uri MusicUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor MusicCursor=MusicResolver.query(MusicUri,null,null,null,null);

        if(MusicCursor!=null && MusicCursor.moveToNext())
        {
            int TitleColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int IdColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int DisplayColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int ArtistColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int AlbumColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int SizeColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int DurationColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int PathColumn=MusicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                long id=MusicCursor.getLong(IdColumn);
                String Title=MusicCursor.getString(TitleColumn);
                String Artist=MusicCursor.getString(ArtistColumn);
                String Album=MusicCursor.getString(AlbumColumn);
                String Size=MusicCursor.getString(SizeColumn);
                long Duration=MusicCursor.getLong(DurationColumn);
                String DisplayName=MusicCursor.getString(DisplayColumn);
                String path=MusicCursor.getString(PathColumn);

                Song s=new Song(id,Title,DisplayName,Artist,Album,Duration,Size,path);
                Song.addToArrayList(s);
                Log.i("HHHHHHHHH",s.getPath());
            }while(MusicCursor.moveToNext());
        }
        Song.ResetTempArray();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Toast.makeText(this, requestCode+" "+grantResults.length+" "+grantResults[0]+grantResults[1]+grantResults[2]+" "+PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                    GetAlbums();
                    GetSongs();
                    Song.getSongsCover();
                    Album.MakeAlbums();
                    for (Map.Entry<String,ArrayList<Song>> album:Album.getAlbumsAndSongs().entrySet())
                    {
                        Log.i("ALBUM",album.getValue().size()+"");
                    }
                    Folder.GetFolders(Song.GetSongs());
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NavigationHelper.LaunchToMain(Splash.this);
                            finish();
                        }
                    },2000);
                }
                return;
            }

        }
    }
}
