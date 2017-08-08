package com.example.hazem.musicplayer.Features.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.hazem.musicplayer.Features.Adapter.AlbumExpandableListAdapter;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;
import com.example.hazem.musicplayer.utils.NavigationHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllAlbumsFragment extends Fragment {

    View view;
    AlbumExpandableListAdapter adapter;
    ExpandableListView expandableListView;
    public AllAlbumsFragment() {
        // Required empty public constructor
    }
    private static AllAlbumsFragment mInstance;
    public static AllAlbumsFragment GetInstance()
    {
        if(mInstance==null)
        {
            mInstance=new AllAlbumsFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.all_albums_fragment, container, false);
        expandableListView= (ExpandableListView) view.findViewById(R.id.expandable_listview);

        adapter=new AlbumExpandableListAdapter(this.getContext());
        expandableListView.setAdapter(adapter);
        SettingEvents();
        return view;
    }

    private void SettingEvents()
    {
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                for(int j=0;j<adapter.getGroupCount();j++)
                {
                    if(j==i)
                    {
                        expandableListView.expandGroup(j,true);
                    }
                    else
                    {
                        expandableListView.collapseGroup(j);
                    }
                }
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                int pos=0;
                Song song= (Song) adapter.getChild(i,i1);
                for(int j=0;j<Song.GetSongs().size();j++)
                {
                    if(song.equals(Song.GetSongs().get(j)))
                    {
                        pos=j;
                        break;
                    }
                }
                NavigationHelper.LaunchToSongScreen((AppCompatActivity)AllAlbumsFragment.this.getActivity(),song,pos);
                return false;
            }
        });

    }

}
