package com.specialtopics.flost.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FormAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public FormAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FormPage1.newInstance(0);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return FormPage2.newInstance(1);
            case 2:
                return FormPage3.newInstance(2);
            case 3:
                return FormPage4.newInstance(3);
            case 4:
                return FormPage5.newInstance(4);
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}

