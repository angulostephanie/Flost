package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.OnFormDataListener;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

import java.util.List;

public class FormPage5 extends Fragment implements OnFormDataListener {
    TextView title;
    Item newItem;
    RecyclerView recyclerView;
    ItemAdapter mAdapter;
    List<Item> mItems = Item.getTemporaryData();
    FormActivity activity;

    public static FormPage5 newInstance(int page) {
        FormPage5 fragment = new FormPage5();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            activity.showNextButton();
            activity.hidePrevButton();

            if(newItem != null && newItem.getType() == "lost")
                activity.setPageOnNextClick(8);
            else
                activity.setPageOnNextClick(6);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (FormActivity) getActivity();
        View view = inflater.inflate(R.layout.form_page5, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        title = view.findViewById(R.id.tv_q8);
        title.setText(Utils.getQ5());

        setUpRecyclerView(view);
    }

    @Override
    public void onFormDataReceived(Item item) {

        newItem = item;
        if(newItem != null) {
            Log.d("test", newItem.getInputDay());
            Log.d("test", newItem.getInputTime());
        }
    }

    @Override
    public void passDataThrough() {
        //if(newItem.getType() == )
    }


    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.match_recycler_view);
        mAdapter = new ItemAdapter(getActivity(), mItems, true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}