package com.ilp.innovations.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.Locale;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private PlaceholderFragment placeholderFragment;
    private UnAllocatedSlotsFragment unAllocatedSlotsFragment;
    private String[] headLines;
    private int tabCount;


    public SectionsPagerAdapter(FragmentManager fm, String[] headLines) {
        super(fm);
        this.headLines = headLines;
        tabCount = headLines.length;
        placeholderFragment = new PlaceholderFragment();
        unAllocatedSlotsFragment = new UnAllocatedSlotsFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return placeholderFragment.newInstance();
            case 1:
                return unAllocatedSlotsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        if(tabCount>=position)
            return this.headLines[position].toUpperCase();
        else
            return null;
    }

}
