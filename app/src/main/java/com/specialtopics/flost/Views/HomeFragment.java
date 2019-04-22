package com.specialtopics.flost.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HomeFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "HomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FloatingActionButton addBtn;
    private List<Item> mItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private View view;
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
        recyclerView = view.findViewById(R.id.home_recycler_view);
        addBtn = view.findViewById(R.id.fabAdd);
        mItems = Item.getTemporaryData();
        setUpRecyclerView();

        addBtn.setOnClickListener(v -> {
            postItemToDB("airpods", "fake description haha", "found",
                    "johnson", 20.0);
        });
    }

    private void postItemToDB(String itemName, String description, String type, String location, double reward) {
        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://10.0.2.2:8080/postItem";
        JSONObject jsonParams = new JSONObject();

        int hash = (itemName + mUser.getUid() + description + type + location).hashCode();

        try {
            jsonParams.put("item_id", hash); // int
            jsonParams.put("user_id", mUser.getUid());
            jsonParams.put("item_name", itemName);
            jsonParams.put("item_desc", description);
            jsonParams.put("item_type", type);
            jsonParams.put("item_location", location);
            jsonParams.put("item_reward", reward); // double

            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.post(mContext, url, entity, "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "Adding this item to mysql :)!");
                        // move update ui function here
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, "faileddddd!");
                    }

                    @Override
                    public void onProgress(long bytesWritten, long totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                        // add a progress bar animation here! :)
                    }
                });
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
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
