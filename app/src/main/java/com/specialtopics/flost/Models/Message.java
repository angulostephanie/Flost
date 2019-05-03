package com.specialtopics.flost.Models;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Message {

    String mUsername;
    String mMessage;
    User mSender;
    long createdAt;

//    public static final int TYPE_MESSAGE = 0;
//    public static final int TYPE_LOG = 1;
//    public static final int TYPE_ACTION = 2;
//
//    private int mType;
//    private String mMessage;
//    private String mUsername;
//
    private Message() {

    }

//    public int getType() {
//        return mType;
//    };

    public String getMessage() {
        return mMessage;
    };

    public String getSender() {
        return mSender.getUserID();
    }

    public long getCreatedAt() {
        return createdAt = System.currentTimeMillis();
    }


    public static class Builder {
        private String mUsername;
        private String mMessage;

        public Builder username(String username) {
            mUsername = username;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            return message;
        }
    }
}
