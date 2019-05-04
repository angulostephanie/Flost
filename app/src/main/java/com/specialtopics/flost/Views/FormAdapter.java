package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.specialtopics.flost.Models.Item;

public class FormAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;
    private static boolean lostItemStatus;
    Fragment frag_1;
    Item newItem;

    public FormAdapter(FragmentManager fragmentManager, Item item) {
        super(fragmentManager);
        newItem = item;
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
            case 0:
                return setUpBundle(newItem, new FormPage1(), 0);
            case 1:
                return setUpBundle(newItem, new FormPage2(), 1);
            case 2:
                return setUpBundle(newItem, new FormPage3(), 2);
            case 3:
                return FormPage4.newInstance(3);
            case 4:
                return FormPage5.newInstance(4);
            default:
                return null;
        }
    }
    public Fragment setUpBundle(Item newItem, Fragment fragment, int page){
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", newItem);
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }
    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }


}

