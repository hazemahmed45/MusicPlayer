package com.example.hazem.musicplayer.Features.Adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by Hazem on 5/20/2017.
 */

public class SongAdapter extends BaseAdapter {
    ArrayList<Song> SongsList;
    Context context;
    LayoutInflater inflater;
    ViewHolder viewHolder;
    public SongAdapter(ArrayList<Pair<Song,Integer>> songs, AppCompatActivity activity) {
        context=activity;
        inflater=LayoutInflater.from(context);
        SongsList=new ArrayList<>();
        for (Pair<Song,Integer> s:songs)
        {
            SongsList.add(s.first);
        }
    }

    @Override
    public int getCount() {
        return SongsList.size();
    }

    @Override
    public Song getItem(int i) {
        return SongsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.song_view, viewGroup, false);
            viewHolder=new ViewHolder();
            viewHolder.Cover= (ImageView) view.findViewById(R.id.songcover);
            viewHolder.Title= (TextView) view.findViewById(R.id.song_title);
            viewHolder.Duration= (TextView) view.findViewById(R.id.song_duration);
            viewHolder.Artist= (TextView) view.findViewById(R.id.song_artist);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) view.getTag();
        }

        if(SongsList.get(i).getCover()==null)
        {
            viewHolder.Cover.setImageResource(R.drawable.songicon);
        }
        else
        {
            viewHolder.Cover.setImageBitmap(SongsList.get(i).getCover());
        }
        viewHolder.Title.setText(SongsList.get(i).getDisplayName());
        viewHolder.Artist.setText(SongsList.get(i).getArtist());
        viewHolder.Duration.setText(String.format("%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(SongsList.get(i).getDuration()),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(SongsList.get(i).getDuration())-
                        java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(SongsList.get(i).getDuration()))));
        return view;
    }
    public static class ViewHolder
    {
        ImageView Cover;
        TextView Title;
        TextView Artist;
        TextView Duration;
    }
}
