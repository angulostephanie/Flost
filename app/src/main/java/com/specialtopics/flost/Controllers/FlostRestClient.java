package com.specialtopics.flost.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.Models.Message;
import com.specialtopics.flost.R;

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
    private final static String MAIN_URL =  "http://52.52.100.133:80"; // "http://10.0.2.2:8080";
    private final static String CONTENT_TYPE = "application/json";
    private static final String GS_BUCKET = "gs://flostapp-1556168232146.appspot.com/";
    private final static AsyncHttpClient client = new AsyncHttpClient();
    private static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance(GS_BUCKET);


    public FlostRestClient() { }

    public static void authenticateUser(Context mContext, String token, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = MAIN_URL + "/authenticateUser";
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("token", token);
            Log.d(TAG, token);
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.post(mContext, url, entity, "application/json", jsonHttpResponseHandler);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void getItems(Context mContext, String type, JsonHttpResponseHandler jsonHttpResponseHandler) {
        Task<GoogleSignInAccount> task = getGoogleSignInTask(mContext);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            FlostRestClient.getItemsHelper(mContext, account.getIdToken(), type, jsonHttpResponseHandler);
        } catch (ApiException e) {
            Log.w(TAG, "Google sign in failed", e);
        }
    }
    private static void getItemsHelper(Context mContext, String token, String type, JsonHttpResponseHandler jsonHttpResponseHandler) {
        String url = MAIN_URL + "/getItems";
        JSONObject jsonParams = new JSONObject();

        try {
            if(!type.isEmpty()) jsonParams.put("item_type", type);
            jsonParams.put("token", token);
            Log.d(TAG, "currently in json params " + jsonParams.toString());
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.get(mContext, url, entity, "application/json", jsonHttpResponseHandler);
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static void postItem(Context mContext, Item item) {
        Task<GoogleSignInAccount> task = getGoogleSignInTask(mContext);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            FlostRestClient.postItemHelper(mContext, account.getIdToken(), item);
        } catch (ApiException e) {
            Log.w(TAG, "Google sign in failed", e);
        }
    }
    private static void postItemHelper(Context mContext, String token, Item item) {

        String url = MAIN_URL + "/postItem";
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("token", token);
            jsonParams.put("item_id", item.getItemID());
            jsonParams.put("email",item.getEmail());
            jsonParams.put("item_date", item.getInputDay());
            jsonParams.put("item_time", item.getInputTime());
            jsonParams.put("item_name", item.getName());
            jsonParams.put("item_desc", "temporary description");
            jsonParams.put("item_type", item.getType());
            jsonParams.put("item_location", item.getLocation());

            if(item.containsStaticImage())  jsonParams.put("static_image_id", item.getStaticImageID());



            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.post(mContext, url, entity, CONTENT_TYPE, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "Adding this item to mysql :)!");
                        if(!item.containsStaticImage()) postItemImage(mContext, item.getImage(), item.getItemID());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, "faileddddd!");
                        Toast.makeText(mContext, "Item did not upload successfully, please try again.", Toast.LENGTH_SHORT).show();
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

    public static void deleteItem(Context mContext, Item item) {
        Task<GoogleSignInAccount> task = getGoogleSignInTask(mContext);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            FlostRestClient.deleteItemHelper(mContext, account.getIdToken(), item);
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e);
        }

    }

    private static void deleteItemHelper(Context mContext, String token, Item item) {
        String url = MAIN_URL + "/deleteItem";
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("token", token);
            Log.d(TAG, "Item id isssss – " + item.getItemID());
            jsonParams.put("item_id", item.getItemID());
            jsonParams.put("email", item.getEmail());
            Log.d(TAG, "deleting, boohoo");
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.delete(mContext, url, entity, CONTENT_TYPE, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "successfully deleted this item from mysql!");

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, "failed!! could not delete this item from mysql :(");
                        Toast.makeText(mContext, "Item was not deleted, please try again.", Toast.LENGTH_SHORT).show();

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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void postMessage(Context mContext, Message message) {
        Task<GoogleSignInAccount> task = getGoogleSignInTask(mContext);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            FlostRestClient.postMessageHelper(mContext, account.getIdToken(), message);
        } catch (ApiException e) {
            Log.w(TAG, "Google sign in failed", e);
        }
    }
    private static void postMessageHelper(Context mContext, String token, Message message) {
        String url = MAIN_URL + "/postMessage";
        JSONObject jsonParams = new JSONObject();
        try {
            // TODO: replace hardcoded `chat_room_id` with message obj's chat room id
            jsonParams.put("chat_room_id", 123); // int
            jsonParams.put("message_id", message.getID()); // int
            jsonParams.put("sender_email", message.getSenderEmail());
            // TODO: replace hardcoded `receiver_email` with receiver's email pls
            jsonParams.put("receiver_email", "fake_user@oxy.edu");
            jsonParams.put("message_content", message.getMessage());
            jsonParams.put("message_timestamp", message.getCreatedAt());


            Log.d(TAG, "message !");
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.post(mContext, url, entity, CONTENT_TYPE, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "Adding this message to mysql :)! " +
                                "[" + message.getMessage() + "]");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, "failed, message can't be sent :/");
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

    public static List<Message> getMessages(Context mContext, String senderEmail, String receiverEmail) {
        Task<GoogleSignInAccount> task = getGoogleSignInTask(mContext);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            return FlostRestClient.getMessagesHelper(mContext, account.getIdToken(), senderEmail, receiverEmail);
        } catch (ApiException e) {
            Log.w(TAG, "Google sign in failed", e);
        }
        return null;
    }
    private static List<Message> getMessagesHelper(Context mContext, String token,
                                                  String senderEmail, String receiverEmail) {
        String url = MAIN_URL + "/getMessages";
        JSONObject jsonParams = new JSONObject();
        final List<Message> messages = new ArrayList<>();

        try {
            jsonParams.put("token", token);
            jsonParams.put("sender_email", senderEmail);
            jsonParams.put("receiver_email", receiverEmail);

            Log.d(TAG, "currently in json params " + jsonParams.toString());
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.get(mContext, url, entity, CONTENT_TYPE, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                        // Pull out the first event on the public timeline
                        Log.d(TAG, "JSONArray returned");
                        Log.d(TAG, array.toString());

                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject obj = array.getJSONObject(i);
                                // TODO: add the chat room id here (Di)
                                Message message = new Message(obj.getInt("message_id"),
                                        obj.getString("sender_email"),
                                        obj.getString("receiver_email"),
                                        obj.getString("message_content"),
                                        obj.getString("message_timestamp"));
                                messages.add(message);
                                Log.d(TAG, message.toString());
                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, "can't fetch messages rn :/");

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

        return messages;
    }

    public static void createChatRoom(Context mContext, int chatRoomID, String ownerEmail) {
        Task<GoogleSignInAccount> task = getGoogleSignInTask(mContext);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            FlostRestClient.createChatRoomHelper(mContext, account.getIdToken(), chatRoomID, ownerEmail);
        } catch (ApiException e) {
            Log.w(TAG, "createChatRoom :: Google sign in failed", e);
        }
    }
    private static void createChatRoomHelper(Context mContext, String token, int chatRoomID, String ownerEmail) {
        String url = MAIN_URL + "/createChatRoom";
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("token", token);
            jsonParams.put("chat_room_id", chatRoomID); // int
            jsonParams.put("owner_email", ownerEmail);

            Log.d(TAG, "chat room creation !");
            try {
                StringEntity entity = new StringEntity(jsonParams.toString());
                client.post(mContext, url, entity, CONTENT_TYPE, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "Adding this chat room to mysql :)! " +
                                "[" + chatRoomID + "]");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, "failed, message can't be sent :/");
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

    private static void postItemImage(Context mContext, byte[] image, int key) {

        StorageReference ref = firebaseStorage.getReference().child("images/"+ key);
        ref.putBytes(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(mContext, "Item was upload successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(mContext, "Item image was not uploaded successfully :/ "
                                + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        Log.d("Progress", String.valueOf(progress));
                    }
                });
    }
    public static void fetchItemImage(Context mContext, Item item, OnCompleteListener<byte[]> completionCallBack) {
        Item itemWithImage = item;
        StorageReference storageRef = firebaseStorage.getReference().child("images/");
        StorageReference imageRef = storageRef.child(Integer.toString(item.getItemID()));
        final long LIMIT = 512 * 512;
        Log.d(TAG, "gonna fetch the item's images now, brb");

        imageRef.getBytes(LIMIT).addOnSuccessListener(bytes -> {
                    Log.d(TAG, "success!");
                    itemWithImage.setImage(bytes);
                }).addOnFailureListener(e -> {
                    Log.d(TAG, "Fetching image failed :/ " + e.getMessage());
                    itemWithImage.setCanFetchImage(false);
                    itemWithImage.setNotFoundImage(mContext);
                }).addOnCompleteListener(completionCallBack);
    }
    private static Task<GoogleSignInAccount> getGoogleSignInTask(Context mContext) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
        Task<GoogleSignInAccount> task = mGoogleSignInClient.silentSignIn();
        return task;
    }
}
