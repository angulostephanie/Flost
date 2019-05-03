package com.specialtopics.flost;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;


public class Utils extends android.support.v4.app.Fragment {
    public void Utils(){
    }

    public static void loadFragment(Fragment fragment, int frameID, FragmentTransaction transaction) {
        // load fragment

        transaction.replace(frameID, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();
    }

}
