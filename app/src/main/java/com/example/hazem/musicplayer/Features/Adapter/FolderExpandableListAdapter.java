package com.example.hazem.musicplayer.Features.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hazem.musicplayer.Models.Folder;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by Hazem on 6/20/2017.
 */

public class FolderExpandableListAdapter extends BaseExpandableListAdapter {
    ArrayList<Folder> folders=null;
    LayoutInflater inflater;
    Context context;
    FolderChildHolder childViewHolder;
    FolderGroupHolder groupViewHolder;
    public FolderExpandableListAdapter(Context context,ArrayList<Folder> folders)
    {
        this.folders=folders;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return folders.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return folders.get(i).getSongs().size();
    }

    @Override
    public Folder getGroup(int i) {
        return folders.get(i);
    }

    @Override
    public Song getChild(int i, int i1) {
        return folders.get(i).getSongs().get(i1);
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
        if(view==null)
        {
            view=inflater.inflate(R.layout.foldername_layout,viewGroup,false);
            groupViewHolder=new FolderGroupHolder();
            groupViewHolder.folderName= (TextView) view.findViewById(R.id.foldername);
            view.setTag(groupViewHolder);
        }
        else
        {
            groupViewHolder= (FolderGroupHolder) view.getTag();
        }
        groupViewHolder.folderName.setText(folders.get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.song_view, viewGroup, false);
            childViewHolder= new FolderChildHolder();
            childViewHolder.SongCover= (ImageView) view.findViewById(R.id.songcover);
            childViewHolder.SongTitle= (TextView) view.findViewById(R.id.song_title);
            childViewHolder.SongDuration= (TextView) view.findViewById(R.id.song_duration);
            childViewHolder.SongArtist= (TextView) view.findViewById(R.id.song_artist);
            view.setTag(childViewHolder);
        }
        else
        {
            childViewHolder= (FolderChildHolder) view.getTag();
        }

        Song song=folders.get(i).getSongs().get(i1);
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
    public class FolderGroupHolder
    {
        TextView folderName;
    }
    public class FolderChildHolder
    {
        ImageView SongCover;
        TextView SongTitle;
        TextView SongDuration;
        TextView SongArtist;
    }
}
