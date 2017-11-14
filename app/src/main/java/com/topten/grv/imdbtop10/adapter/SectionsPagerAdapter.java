package com.topten.grv.imdbtop10.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.topten.grv.imdbtop10.TopMoviesFragment;
import com.topten.grv.imdbtop10.MoreFragment;

/**
 * Created by grv on 11-11-2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

            TopMoviesFragment topMoviesFragment = new TopMoviesFragment();
            return topMoviesFragment;

            case 1:
                MoreFragment moreFragment = new MoreFragment();
                return moreFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "Top 10";

            case 1:
                return "More";

            default:
                return null;
        }

    }
}
