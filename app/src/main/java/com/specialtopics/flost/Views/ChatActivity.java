package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.Message;
import com.specialtopics.flost.Models.MessageListAdapter;
import com.specialtopics.flost.R;

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

//        List<Message> messages = mMessageAdapter.getmMessageList();
        if(mUser.getEmail() != null) {
            // TODO TBD whether to pass in room id or sender receiver email
            List<Message> messages = FlostRestClient.getMessages(this, mUser.getEmail(), receiverEmail);

            mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
            mMessageAdapter = new MessageListAdapter(this, messages);

            mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
