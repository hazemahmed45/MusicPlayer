package com.example.hazem.musicplayer.Models;

import android.graphics.Bitmap;
import android.util.Pair;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * Created by Hazem on 5/20/2017.
 */

public class Song implements Serializable {
    private long ID;
    private String Title;
    private String DisplayName;
    private String Artist;
    private String Album;
    private long Duration;
    private String Size;
    private String Path;
    private Bitmap Cover;
    private static ArrayList<Song> AllSongs;
    private static ArrayList<Pair<Song,Integer>> TempSongArrayList;
    public static ArrayList<Song> GetSongs()
    {
        if(AllSongs==null)
        {
            AllSongs=new ArrayList<>();
        }
        return AllSongs;
    }
    public static ArrayList<Pair<Song, Integer>> GeTemptSongs()
    {
        if(TempSongArrayList==null)
        {
            TempSongArrayList=new ArrayList<>();
        }
        return TempSongArrayList;
    }
    public static void NewTempArrayList()
    {
        TempSongArrayList=new ArrayList<>();
    }
    public static void ResetTempArray()
    {
        TempSongArrayList=new ArrayList<>();
        for (int i=0;i<GetSongs().size();i++)
        {
            GeTemptSongs().add(new Pair<Song, Integer>(GetSongs().get(i),i));
        }
    }
    public static void addToArrayList(Song s)
    {
        GetSongs().add(s);
        Collections.sort(AllSongs, new Comparator<Song>() {
            @Override
            public int compare(Song song, Song t1) {

                return song.getDisplayName().compareTo(t1.getDisplayName());
            }
        });
    }

    public static void getSongsCover()
    {
        for (Song song:AllSongs)
        {
            if(com.example.hazem.musicplayer.Models.Album.getAlbumsMap().containsKey(song.getAlbum()))
            {
                song.setCover(com.example.hazem.musicplayer.Models.Album.getAlbumsMap().get(song.getAlbum()).getCover());
            }
        }
    }
    public Song(long ID, String title,String displayed, String artist, String album, long duration, String size,String path) {
        this.ID = ID;
        Title = title;
        DisplayName=displayed;
        Artist = artist;
        Album = album;
        Duration = duration;
        Size = size;
        Path=path;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public Bitmap getCover() {
        return Cover;
    }

    public void setCover(Bitmap cover) {
        Cover = cover;
    }
}
