package com.specialtopics.flost.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "HomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private List<Item> mItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private View view;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private ToggleButton mapToggle;
    private Utils utils = new Utils();
    FragmentTransaction transaction;

    public HomeFragment(){

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mContext = getContext();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        frameLayout = view.findViewById(R.id.result_framelayout);


        setUpTabListeners();
        setUpMapToggle();

    }

    private void setUpMapToggle(){
        mapToggle = view.findViewById(R.id.toggleButton);
        mapToggle.setOnClickListener(v -> {
            Fragment fragment;
            if(mapToggle.isChecked()){
                fragment = new MapsActivity();
            } else {
                fragment = new HomeFragmentLost();
            }
            transaction = getFragmentManager().beginTransaction();
            Utils.loadFragment(fragment, R.id.frame_container, transaction);
        });
    }
    private void setUpTabListeners(){
        tabLayout = view.findViewById(R.id.tabLayout);
        transaction = getFragmentManager().beginTransaction();
        Utils.loadFragment(new HomeFragmentLost(), R.id.result_framelayout, transaction);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomeFragmentLost();
                        break;
                    case 1:
                        fragment = new HomeFragmentFound();
                        break;
                }
                transaction = getFragmentManager().beginTransaction();
                Utils.loadFragment(fragment, R.id.result_framelayout, transaction);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
