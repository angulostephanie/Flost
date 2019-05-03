package com.specialtopics.flost.Controllers;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.specialtopics.flost.Models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class FlostRestClient {
    private final static String TAG = "FlostRestClient";
    private final static String MAIN_URL = "http://10.0.2.2:8080";
    private final static AsyncHttpClient client = new AsyncHttpClient();

    public FlostRestClient() {
    }

    public static List<Item> getItems(Context mContext, String type) {
        String url = MAIN_URL + "/getItems";
        JSONObject jsonParams = new JSONObject();
        final List<Item> items = new ArrayList<>();

        try {
            if(!type.isEmpty()) jsonParams.put("item_type", type);

            Log.d(TAG, "currently in json params " + jsonParams.toString());
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.get(mContext, url, entity, "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                        // Pull out the first event on the public timeline
                        Log.d(TAG, "JSONArray returned");
                        Log.d(TAG, array.toString());

                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject obj = array.getJSONObject(i);
                                Item item = new Item(obj.getInt("item_id"),
                                        obj.getString("email"),
                                        obj.getString("item_name"),
                                        obj.getString("item_desc"),
                                        obj.getString("item_type"),
                                        obj.getString("item_location"),
                                        obj.getString("item_timestamp"),
                                        obj.getDouble("item_reward"));
                                items.add(item);
                                Log.d(TAG, item.toString());
                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
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
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }


        return items;
    }

    public static void postItemToDB(Context mContext, Item item) {

        String url = MAIN_URL + "/postItem";
        JSONObject jsonParams = new JSONObject();


        try {
            jsonParams.put("item_id", item.getItemID()); // int
            jsonParams.put("email",item.getEmail());
            jsonParams.put("item_name", item.getName());
            jsonParams.put("item_desc", item.getDesc());
            jsonParams.put("item_type", item.getType());
            jsonParams.put("item_location", item.getLocation());
            jsonParams.put("item_reward", item.getReward()); // double

            Log.d(TAG, "helloooooooo!");
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.post(mContext, url, entity, "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "Adding this item to mysql :)!");
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


    public static void authenticateUser(Context mContext, String token) {
        String url = MAIN_URL + "/authenticateUser";
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("token", token);
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
