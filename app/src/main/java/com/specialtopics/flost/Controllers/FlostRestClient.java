package com.specialtopics.flost.Controllers;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class FlostRestClient {
    private final static String TAG = "FlostRestClient";
    private final static AsyncHttpClient client = new AsyncHttpClient();

    public FlostRestClient(){
    }

    public static void postItemToDB(FirebaseUser mUser, Context mContext, String itemName, String description, String type, String location, double reward) {

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


    public static void addUserToDB(Context mContext, FirebaseUser user) {
        AsyncHttpClient client = new AsyncHttpClient();

        String url = "http://10.0.2.2:8080/loginUser";

        String first = user.getDisplayName().split(" ")[0];

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("user_id", user.getUid());
            jsonParams.put("first_name", first);
            jsonParams.put("email", user.getEmail());

            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.post(mContext, url, entity, "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "Adding this user to the mysql :)!");
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
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
