package com.example.hazem.musicplayer.Features.Fragment;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazem.musicplayer.Features.Activity.MainScreen;
import com.example.hazem.musicplayer.Features.Activity.SongScreen;
import com.example.hazem.musicplayer.Features.Adapter.SongAdapter;
import com.example.hazem.musicplayer.Features.Services.MusicPlayerService;
import com.example.hazem.musicplayer.Models.Song;
import com.example.hazem.musicplayer.R;
import com.example.hazem.musicplayer.utils.NavigationHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllSongsFragment extends Fragment {


    public AllSongsFragment() {
        // Required empty public constructor
    }
    View view;
    ListView listview;
    SongAdapter adapter;
    MaterialSearchBar searchView;

    private static AllSongsFragment mInstance;
    public static AllSongsFragment GetInstance()
    {
        if(mInstance==null)
        {
            mInstance=new AllSongsFragment();
        }
        return mInstance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.all_songsfragment, container, false);
        settingIDs();
        settingEvents();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter=new SongAdapter(Song.GeTemptSongs(),(AppCompatActivity) this.getActivity());
        listview.setAdapter(adapter);
    }

    void settingIDs()
    {
        listview= (ListView) view.findViewById(R.id.song_listview);
        searchView= (MaterialSearchBar) view.findViewById(R.id.song_search_view);
        settingEvents();
    }
    void settingEvents()
    {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Song s=adapter.getItem(i);
                int pos=Song.GeTemptSongs().get(i).second;
                NavigationHelper.LaunchToSongScreen(AllSongsFragment.this.getActivity(),s,pos);

            }
        });
        searchView.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                Toast.makeText(AllSongsFragment.this.getContext(), enabled+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Song.NewTempArrayList();
                for (int i=0;i<Song.GetSongs().size();i++)
                {
                    if(Song.GetSongs().get(i).getDisplayName().contains(text.toString()))
                    {
                        Song.GeTemptSongs().add(new Pair<Song, Integer>(Song.GetSongs().get(i),i));

                    }
                }
                adapter=new SongAdapter(Song.GeTemptSongs(),(AppCompatActivity)AllSongsFragment.this.getActivity());
                listview.setAdapter(adapter);
                searchView.enableSearch();

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        searchView.setHint("Search");
        searchView.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i3, int i1, int i2) {
                Song.NewTempArrayList();
                for (int i=0;i<Song.GetSongs().size();i++)
                {
                    if(Song.GetSongs().get(i).getDisplayName().contains(charSequence.toString()))
                    {
                        Song.GeTemptSongs().add(new Pair<Song, Integer>(Song.GetSongs().get(i),i));

                    }
                }
                adapter=new SongAdapter(Song.GeTemptSongs(),(AppCompatActivity) AllSongsFragment.this.getActivity());
                listview.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
