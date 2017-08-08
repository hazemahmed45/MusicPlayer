package com.example.hazem.musicplayer.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hazem on 5/31/2017.
 */

public class Album
{
    private String Name;
    private String Artist;
    private Bitmap Cover;

    private static HashMap<String,Album> AlbumMap;
    private static HashMap<String,ArrayList<Song>> AlbumsAndSongs;

    public static HashMap<String,Album> getAlbumsMap()
    {
        if(AlbumMap==null)
        {
            AlbumMap=new HashMap<>();
        }
        return AlbumMap;
    }
    public static HashMap<String,ArrayList<Song>> getAlbumsAndSongs()
    {
        if(AlbumsAndSongs==null)
        {
            AlbumsAndSongs=new HashMap<>();
        }
        return AlbumsAndSongs;
    }
    public static void MakeAlbums()
    {
        for (Song s:Song.GetSongs())
        {
            if(getAlbumsMap().containsKey(s.getAlbum()))
            {
                if(getAlbumsAndSongs().containsKey(s.getAlbum()))
                {
                    getAlbumsAndSongs().get(s.getAlbum()).add(s);
                }
                else
                {
                    getAlbumsAndSongs().put(s.getAlbum(),new ArrayList<Song>());
                    getAlbumsAndSongs().get(s.getAlbum()).add(s);
                }
            }
        }
    }
    public Album(String name, String artist, Bitmap cover) {
        Name = name;
        Artist = artist;
        Cover = cover;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public Bitmap getCover() {
        return Cover;
    }

    public void setCover(Bitmap cover) {
        Cover = cover;
    }



}
