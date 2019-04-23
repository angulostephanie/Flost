package com.specialtopics.flost.Controllers;

import android.app.Application;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class ChatApplication extends Application {

    private Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public Socket getmSocket(){
        return mSocket;
    }
}
