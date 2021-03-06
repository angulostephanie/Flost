package com.specialtopics.flost;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.specialtopics.flost.Views.FormActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Utils extends android.support.v4.app.Fragment {
    private static final String TAG = "Utils";
    public void Utils(){}

    public static String getQ1(){ return "Did you..."; }
    public static String getQ2(){ return "What is the item called?"; }
    public static String getQ3(String verb){ return "Where did you " + verb + " it?"; }
    public static String getQ4(String verb){ return "When did you " + verb + " it?"; }
    public static String getQ5() {return  "Do any of these items look like a match?";}
    public static String getQ6_1() { return "Do you plan on leaving it with someone else?"; }
    public static String getQ6_2() { return "Who are you leaving it with?"; }
    public static String getQ7_1() { return "Do you want to set up a verification question for the owner?"; }
    public static String getQ7_2() { return "What is the verification question?"; }
    public static String getQ8(){ return "Let's upload a photo of it!"; }


    public static Fragment getNextFrag(FragmentActivity activity, int currentPage){
        Fragment nextFrag = activity.getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":" + (currentPage + 1));
        return nextFrag;
    }

    public static String getVerb(String itemType){
        String verb;
        if(itemType == "lost")
            verb = "lose";
        else
            verb = "find";

        return verb;
    }
    public static void loadFragment(Fragment fragment, int frameID, FragmentTransaction transaction) {
        // load fragment

        transaction.replace(frameID, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();
    }

    public static void setUpStartFormBtns(FloatingActionButton addBtn, Activity activity){
        addBtn.setOnClickListener(v -> {
            Intent mIntent = new Intent(activity, FormActivity.class);
            activity.startActivity(mIntent);
        });
    }


    public static String[] getRecentDays(){
        String[] days = new String[8];
        days[0] = "Day";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd ");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 0);

        for(int i = 1; i < 8; i++){
            if(i == 1)
                days[i] = "Today";
            else if(i == 2)
                days[i] = "Yesterday";
            else
                days[i] = sdf.format(cal.getTime());
            cal.add(Calendar.DAY_OF_YEAR, -1);

        }
        return days;
    }
}