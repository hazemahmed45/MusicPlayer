package com.example.hazem.musicplayer.Features.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.hazem.musicplayer.Features.Adapter.AlbumExpandableListAdapter;
import com.example.hazem.musicplayer.Features.Adapter.FolderExpandableListAdapter;
import com.example.hazem.musicplayer.Models.Folder;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;
import com.example.hazem.musicplayer.utils.NavigationHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFoldersFragment extends Fragment {


    public AllFoldersFragment() {
        // Required empty public constructor
    }
    ArrayList<Folder> folders=null;
    View view;
    ExpandableListView expandableListView;
    FolderExpandableListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.all_folders_fragment, container, false);
        SettingIDs();
        //Toast.makeText(AllFoldersFragment.this.getContext(), "here", Toast.LENGTH_SHORT).show();
        return view;
    }
    void SettingIDs()
    {
        View v=view;
        MakeFoldersList(Folder.getmInstance());
        expandableListView= (ExpandableListView) v.findViewById(R.id.folder_explistview);
       // Toast.makeText(AllFoldersFragment.this.getContext(), folders.size()+"", Toast.LENGTH_SHORT).show();
        adapter=new FolderExpandableListAdapter(getContext(),folders);
        expandableListView.setAdapter(adapter);
        SettingEvents();
    }
    void MakeFoldersList(HashMap <String ,Folder> folderHashMap)
    {
        ArrayList<Folder> folder=new ArrayList<>();
        for (Map.Entry<String,Folder> entry:folderHashMap.entrySet()) {
            folder.add(entry.getValue());
            Log.i("Folder",entry.getValue().getSongs().size()+"");

        }
        folders=folder;
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
                NavigationHelper.LaunchToSongScreen((AppCompatActivity)AllFoldersFragment.this.getActivity(),song,pos);
                return false;
            }
        });

    }
}
