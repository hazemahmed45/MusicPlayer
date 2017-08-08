package com.example.hazem.musicplayer.Features.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hazem.musicplayer.Features.Fragment.AllAlbumsFragment;
import com.example.hazem.musicplayer.Features.Fragment.AllFoldersFragment;
import com.example.hazem.musicplayer.Features.Fragment.AllSongsFragment;

/**
 * Created by Hazem on 6/1/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return AllSongsFragment.GetInstance();
            case 1:
                return AllAlbumsFragment.GetInstance();
            case 2:
                return new AllFoldersFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
            {
                return "All Songs";
            }
            case 1:
                return "All Albums";
            case 2:
                return "All Folders";
        }
        return super.getPageTitle(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }
}
