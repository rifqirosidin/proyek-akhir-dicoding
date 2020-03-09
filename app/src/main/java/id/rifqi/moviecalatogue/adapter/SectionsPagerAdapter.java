package id.rifqi.moviecalatogue.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import id.rifqi.moviecalatogue.ui.fragment.MovieFragment;
import id.rifqi.moviecalatogue.ui.fragment.TvShowFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;


    public SectionsPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a MovieFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new MovieFragment();

            case 1:
                return new TvShowFragment();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return numOfTabs;
    }
}