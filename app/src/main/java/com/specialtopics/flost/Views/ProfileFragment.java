package com.specialtopics.flost.Views;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.specialtopics.flost.Controllers.ChatApplication;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ProfileFragment";
    private static final int FROM_HOME_RESULT_CODE = 12;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Context mContext;
    private Button logoutBtn;
    private TextView emailTextView;
    private Socket mSocket;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private List<Item> mItems = new ArrayList<>();
    private String email;

    public ProfileFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getContext();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        email = mUser.getEmail();
        fetchItems(email);
        recyclerView = view.findViewById(R.id.profile_recycler_view);
        setUpRecyclerView();
        Log.d("UserID", mUser.getUid());

        emailTextView = view.findViewById(R.id.profile_header_tv);
        emailTextView.setText("Hello, " + email + "! :)");
        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> logout());

        return view;
    }

    private void logout() {
        // mUser becomes null after this.
        mAuth.signOut();
        updateUI();
        mSocket.off("login", onLogin);
    }
    private void updateUI() {
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        startActivity(loginIntent);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
//            JSONObject data = (JSONObject) args[0];

//            int numUsers;
//            try {
//                numUsers = data.getInt("numUsers");
//            } catch (JSONException e) {
//                return;
//            }

            Intent intent = new Intent();
            intent.putExtra("username", mAuth.getCurrentUser().getDisplayName());
//            intent.putExtra("numUsers", numUsers);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        }
    };

    private void fetchItems(String email) {
        FlostRestClient.getUsersItems(mContext, email, new JsonHttpResponseHandler() {
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
        mAdapter = new ItemAdapter(getActivity(), mItems, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnItemTouchListener(new MyRecyclerItemClickListener(getContext(),
                (view, position) -> {

                    Item selectedItem = mItems.get(position);
                    Intent intent = new Intent(getContext(), ItemDetailActivity.class);
                    intent.putExtra("item", selectedItem);
                    startActivityForResult(intent, FROM_HOME_RESULT_CODE);
                }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FROM_HOME_RESULT_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Intent refreshIntent = new Intent(mContext, MainActivity.class);
                startActivity(refreshIntent);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

}
