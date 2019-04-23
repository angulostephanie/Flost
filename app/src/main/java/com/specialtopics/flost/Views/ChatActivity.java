package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.specialtopics.flost.Controllers.ChatApplication;
import com.specialtopics.flost.R;

import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_fragment);
        ChatApplication app = (ChatApplication)getApplication();
        mSocket = app.getmSocket();

        if (mSocket.connected()){
            Toast.makeText(ChatActivity.this, "Connected!!",Toast.LENGTH_SHORT).show();
        }
    }
}
