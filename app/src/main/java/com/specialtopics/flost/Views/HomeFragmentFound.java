package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

import java.util.List;

public class HomeFragmentFound extends android.support.v4.app.Fragment {
    ItemAdapter mAdapter;
    RecyclerView recyclerView;
    List<Item> mItems = Item.getTemporaryData();

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
        recyclerView = view.findViewById(R.id.found_recycler_view);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mAdapter = new ItemAdapter(getActivity(), mItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

}
