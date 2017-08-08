package com.example.hazem.musicplayer.Features.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hazem.musicplayer.Models.Album;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hazem on 6/2/2017.
 */

public class AlbumExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    LayoutInflater inflater;
    private ArrayList<Album> AlbumList;
    private HashMap<String, ArrayList<Song>> SongList;
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;

    public AlbumExpandableListAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        AlbumList=new ArrayList<>();
        for (Map.Entry<String,Album> a:Album.getAlbumsMap().entrySet()) {
            AlbumList.add(a.getValue());
        }
        SongList=Album.getAlbumsAndSongs();
    }

    @Override
    public int getGroupCount() {
        return AlbumList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return Album.getAlbumsAndSongs().get(AlbumList.get(i).getName()).size();
    }

    @Override
    public Object getGroup(int i) {
        return AlbumList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return SongList.get(AlbumList.get(i).getName()).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Album album=AlbumList.get(i);
        int size=Album.getAlbumsAndSongs().get(AlbumList.get(i).getName()).size();
        if(view==null)
        {
            view=inflater.inflate(R.layout.album_view,viewGroup,false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.AlbumCover= (ImageView) view.findViewById(R.id.album_drawable);
            groupViewHolder.AlbumName= (TextView) view.findViewById(R.id.album_name);
            groupViewHolder.AlbumNumber= (TextView) view.findViewById(R.id.album_number);
            view.setTag(groupViewHolder);
        }
        else
        {
            groupViewHolder= (GroupViewHolder) view.getTag();
        }
        groupViewHolder.AlbumCover.setImageBitmap(album.getCover());
        groupViewHolder.AlbumName.setText(album.getName());
        groupViewHolder.AlbumNumber.setText(size+" Songs");
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.song_view, viewGroup, false);
            childViewHolder= new ChildViewHolder();
            childViewHolder.SongCover= (ImageView) view.findViewById(R.id.songcover);
            childViewHolder.SongTitle= (TextView) view.findViewById(R.id.song_title);
            childViewHolder.SongDuration= (TextView) view.findViewById(R.id.song_duration);
            childViewHolder.SongArtist= (TextView) view.findViewById(R.id.song_artist);
            view.setTag(childViewHolder);
        }
        else
        {
            childViewHolder= (ChildViewHolder) view.getTag();
        }

        Song song=SongList.get(AlbumList.get(i).getName()).get(i1);
        childViewHolder.SongTitle.setText(song.getDisplayName());
        childViewHolder.SongArtist.setText(song.getArtist());
        childViewHolder.SongDuration.setText(String.format("%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(song.getDuration()),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(song.getDuration())-
                        java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(song.getDuration()))));

        childViewHolder.SongCover.setImageBitmap(song.getCover());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static class ChildViewHolder
    {
        ImageView SongCover;
        TextView SongTitle;
        TextView SongDuration;
        TextView SongArtist;
    }
    public static class GroupViewHolder
    {
        ImageView AlbumCover;
        TextView AlbumName;
        TextView AlbumNumber;
    }
}
