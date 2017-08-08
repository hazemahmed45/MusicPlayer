package com.example.hazem.musicplayer.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hazem on 6/20/2017.
 */

public class Folder
{
    private String name;
    private String path;
    private ArrayList<Song> songs=null;
    private static HashMap<String,Folder> mInstance=null;
    public static HashMap<String,Folder> getmInstance()
    {
        if(mInstance==null)
        {
            mInstance= new HashMap<>();
        }
        return mInstance;
    }
    public static void GetFolders(ArrayList<Song> songs)
    {
        getmInstance();
        for (Song s:songs)
        {
            String [] pathSplit=s.getPath().split("/");
            String folderName=pathSplit[pathSplit.length-2];
            String folderPath="";
            StringBuffer stringBuffer=new StringBuffer(folderPath);
            for(int i=0;i<pathSplit.length;i++)
            {
                if(i==pathSplit.length-1)
                {
                    break;
                }
                stringBuffer.append(pathSplit[i]+"/");
            }
            folderPath=stringBuffer.toString();
            if(mInstance.containsKey(folderName)==false)
            {
                mInstance.put(folderName,new Folder());
                mInstance.get(folderName).setName(folderName);
                mInstance.get(folderName).setPath(folderPath);
                mInstance.get(folderName).getSongs().add(s);
            }
            if(mInstance.containsKey(folderName)==true)
            {
                mInstance.get(folderName).getSongs().add(s);
            }


        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Song> getSongs() {
        if(songs==null)
        {
            songs=new ArrayList<>();
        }
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
