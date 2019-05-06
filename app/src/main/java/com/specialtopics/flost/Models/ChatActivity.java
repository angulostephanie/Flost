package com.specialtopics.flost.Models;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.Message;
import com.specialtopics.flost.Models.MessageListAdapter;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Views.MessagesFragment;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private FirebaseUser mUser;
    private static final String TAG = "ChatActivity";

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private String receiverEmail;
    private Integer chatroomId;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_container);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        receiverEmail = getIntent().getExtras().getString("receiver_email");
        chatroomId = getIntent().getExtras().getInt("chatroom_id");
        Log.d(TAG, "receiver email: " + receiverEmail);
        Log.d(TAG, "chatroom id: " + chatroomId);

        MessagesFragment messagesFragment = MessagesFragment.newInstace(chatroomId, receiverEmail);
//        Bundle bundle = new Bundle();
//        bundle.putString("receiver_email", receiverEmail);
//        bundle.putInt("chatroom_id", chatroomId);
//        messagesFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, messagesFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();



////        List<Message> messages = mMessageAdapter.getmMessageList();
//        if(mUser.getEmail() != null) {
//            List<Message> messages = FlostRestClient.getMessages(this, chatroomId, mUser.getEmail());
//
//            mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
//            mMessageAdapter = new MessageListAdapter(this, messages);
//
//            mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
//        }
    }
}
