package com.specialtopics.flost.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

import java.util.List;

public class HomeFragmentFound extends android.support.v4.app.Fragment {
    ItemAdapter mAdapter;
    RecyclerView recyclerView;
    List<Item> mItems = Item.getTemporaryData();
    FloatingActionButton addBtn;
    FirebaseUser mUser;
    Context mContext;

    public HomeFragmentFound() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_found, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mContext = getContext();

        setUpRecyclerView(view);
        Utils.setUpStartFormBtns(view.findViewById(R.id.fabAdd), getActivity());

    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.found_recycler_view);
        mAdapter = new ItemAdapter(getActivity(), mItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

}
