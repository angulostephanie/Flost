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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeFragmentLost extends android.support.v4.app.Fragment {
    private static final String TAG = "HomeFragmentLost";
    Context mContext;
    ItemAdapter mAdapter;
    List<Item> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton addBtn;
    public HomeFragmentLost() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_lost, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mContext = getContext();
        mAdapter = new ItemAdapter(mContext, mItems, false);
        recyclerView = view.findViewById(R.id.lost_recycler_view);
        setUpRecyclerView();
        fetchItems("lost");

        addBtn = view.findViewById(R.id.fabAdd);
        Utils.setUpStartFormBtns(addBtn, getActivity());
    }

    private void fetchItems(String type) {
        FlostRestClient.getItems(mContext, type, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {

                Log.d(TAG, "JSONArray returned");
                Log.d(TAG, array.toString());
                for(int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject obj = array.getJSONObject(i);
                        int staticImageID = obj.getInt("static_image_id");
                        if(staticImageID == -1) {
                            // image is hosted on firebase
                            Item item = new Item(obj.getInt("item_id"),
                                    obj.getString("email"),
                                    obj.getString("item_name"),
                                    obj.getString("item_desc"),
                                    obj.getString("item_date"),
                                    obj.getString("item_time"),
                                    obj.getString("item_type"),
                                    obj.getString("item_location"),
                                    obj.getString("item_timestamp"));

                            new Thread() {
                                public void run() { FlostRestClient.fetchItemImage(mContext, item, task -> {
                                    mAdapter.notifyDataSetChanged();
                                    Log.d(TAG, "adapter size :" + mAdapter.getItemCount());
                                }); }

                            }.run();


                            mItems.add(item);

                            Log.d(TAG, "item #" + i + " [" +item.toString() + "]");

                        } else {
                            // find the static image in the android resource folder
                        }
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "size of items " + mItems.size());
                Log.d(TAG, "SWITCHING");
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "madapter " + mAdapter.getItemCount());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d(TAG, "can't fetch items rn :/");

            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                // add a progress bar animation here! :)
            }
        });
    }
    private void setUpRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
