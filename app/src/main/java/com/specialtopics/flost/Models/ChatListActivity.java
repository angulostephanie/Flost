package com.specialtopics.flost.Models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.ChatActivity;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Views.MessagesFragment;

public class ChatListActivity extends android.support.v4.app.Fragment {

    private static final String TAG = "ChatListActivity";
    private Context mContext;
    private Button startChat;
    private String receiverEmail;
    private FirebaseUser messageReceiver;
    private String curretUserEmail;
    private MessagesFragment messagesFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.chatroom_list, null, false);

        Log.d(TAG, "view created");


        /* Later when accepting real users on app, can do recycler view as well, or list view
        List<Chatroom> chatrooms = mChatroomAdapter.getChatrooms();

        mChatroomRecycler = (RecyclerView) findViewById(R.id.reyclerview_chatroom_list);
        mChatroomAdapter = new mChatroomAdapter(this, chatrooms);

        mChatroomRecycler.setLayoutManager(new LinearLayoutManager(this));
        */

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        /**
         * Note:
         * if the post item function is done, change messageReceiver to the user
         * that owns the item
         */
        messageReceiver = FirebaseAuth.getInstance().getCurrentUser();
        String receiverEmail = messageReceiver.getEmail();

        // sender's email
        curretUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Log.d(TAG, "curr user email: " + curretUserEmail);


        // create unique chatroom ID
        Integer chatroomID = createChatroomId(curretUserEmail, receiverEmail);

        Log.d(TAG, "chatroom id: " + chatroomID);




        mContext = getContext();
        startChat = view.findViewById(R.id.echo_chat);
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                messagesFragment = new MessagesFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("receiver_email", receiverEmail);
//                bundle.putInt("chatroom_id", chatroomID);
//                messagesFragment.setArguments(bundle);
                FlostRestClient.createChatRoom(mContext, chatroomID, curretUserEmail, receiverEmail);


                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("chatroom_id", chatroomID);
                intent.putExtra("receiver_email", receiverEmail);
                startActivity(intent);

            }
        });


    }

    private int createChatroomId(String sender, String receiver){
        Integer id = -1;
        String idBeforeHash = sender;

        Integer compare = sender.compareToIgnoreCase(receiver);

        if(compare >= 0){
            idBeforeHash = idBeforeHash + receiver;
        } else {
            idBeforeHash = receiver + idBeforeHash;
        }

        id = idBeforeHash.hashCode();


        return id;
    }


}
