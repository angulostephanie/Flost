package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.specialtopics.flost.Controllers.ChatApplication;
import com.specialtopics.flost.Models.MessageListAdapter;
import com.specialtopics.flost.R;

import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {
//
//    private RecyclerView mMessageRecycler;
//    private MessageListAdapter mMessageAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

//        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        //mMessageAdapter = new MessageListAdapter(this, messageList);
//        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}