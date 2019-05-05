package com.specialtopics.flost.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

import com.specialtopics.flost.Controllers.ChatApplication;
import com.specialtopics.flost.Models.Message;
import com.specialtopics.flost.Models.MessageListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MessagesFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MessageFragment";

    private static final int REQUEST_LOGIN = 0;

    private RecyclerView mMessagesView;
    private List<Message> mMessages = new ArrayList<Message>();
    private EditText mInputMessageView;
    private RecyclerView.Adapter mAdapter;
    private Socket mSocket;
    private String mUsername;
    private Boolean isConnected = true;
    private FirebaseUser mUser;

//    private Handler mTypingHandler = new Handler();

    public MessagesFragment(){
        super();
    }

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MessageListAdapter(context, mMessages);
        if (context instanceof Activity){
            //this.listener = (MainActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUsername = mUser.getDisplayName();

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String room = "temp room";

        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        Log.i(TAG, "got socket!");
        // TODO add server call back function
        //mSocket.emit("join-room", room);
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("new message", onNewMessage);
//        mSocket.on("typing", onTyping);
//        mSocket.on("stop typing", onStopTyping);
        mSocket.connect();

        Boolean bool = mSocket.connected();
        Log.d(TAG, "socket created, connected? " + bool);

//        startSignIn();
        return inflater.inflate(R.layout.activity_message_list, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("new message", onNewMessage);
//        mSocket.off("user joined", onUserJoined);
//        mSocket.off("user left", onUserLeft);
//        mSocket.off("typing", onTyping);
//        mSocket.off("stop typing", onStopTyping);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "creating view!");

        mMessagesView = (RecyclerView) view.findViewById(R.id.reyclerview_message_list);
        mMessagesView.setAdapter(mAdapter);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mInputMessageView = (EditText) view.findViewById(R.id.edittext_chatbox);
        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.sent_message_body || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });
        mInputMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == mUsername) return;
                if (!mSocket.connected()) return;

//                if (!mTyping) {
//                    mTyping = true;
//                    mSocket.emit("typing");
//                }

//                mTypingHandler.removeCallbacks(onTypingTimeout);
//                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
             }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        Button sendButton = (Button) view.findViewById(R.id.button_chatbox_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
                Log.i(TAG, "sent action attempted");
            }
        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (Activity.RESULT_OK != resultCode) {
//            getActivity().finish();
//            return;
//        }
//
//        mUsername = data.getStringExtra("username");
////        int numUsers = data.getIntExtra("numUsers", 1);
//
////        addLog(getResources().getString(R.string.message_welcome));
////        addParticipantsLog(numUsers);
//    }

    private void attemptSend() {
        Log.i(TAG, "Username: " + mUsername);
        if (null == mUsername) return;
        if (!mSocket.connected()) {
            Log.d(TAG, "socket not connected");
            return;
        }

        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }

        mInputMessageView.setText("");
        addMessage(mUsername, message);

        Log.d(TAG, mUsername + " sent message: " + message);

        // perform the sending message attempt.
        mSocket.emit("new message", message);
    }


//    private void startSignIn() {
//        mUsername = null;
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivityForResult(intent, REQUEST_LOGIN);
//    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        if(null!=mUsername)
                            mSocket.emit("add user", mUsername);
                        Toast.makeText(getActivity().getApplicationContext(),
                                R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }

//                    removeTyping(username);
                    addMessage(username, message);
                }
            });
        }
    };

    private void addMessage(String username, String message) {
        Message newMessage = new Message(username, mUser.getEmail(), message);
        //TODO push message object to server
        FlostRestClient.postMessage(getContext(), newMessage);

        mMessages.add(newMessage);
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        Log.e(TAG, "item inserted notified.");
        scrollToBottom();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

//    private Runnable onTypingTimeout = new Runnable() {
//        @Override
//        public void run() {
//            if (!mTyping) return;
//
//            mTyping = false;
//            mSocket.emit("stop typing");
//        }
//    };

//    private Emitter.Listener onTyping = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
//                    try {
//                        username = data.getString("username");
//                    } catch (JSONException e) {
//                        Log.e(TAG, e.getMessage());
//                        return;
//                    }
//                    addTyping(username);
//                }
//            });
//        }
//    };

//    private void addTyping(String username) {
//        mMessages.add(new Message.Builder(Message.TYPE_ACTION)
//                .username(username).build());
//        mAdapter.notifyItemInserted(mMessages.size() - 1);
//        scrollToBottom();
//    }

}
