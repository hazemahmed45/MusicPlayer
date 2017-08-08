package com.example.hazem.musicplayer.Models;

import java.util.ArrayList;

/**
 * Created by Hazem on 5/31/2017.
 */

public class Artist
{
    private String Name;

    private static ArrayList<Artist> AllArtists;

    public static ArrayList<Artist> getArtists()
    {
        if(AllArtists==null)
        {
            AllArtists=new ArrayList<>();
        }
        return AllArtists;
    }
    public Artist(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
