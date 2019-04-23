package com.specialtopics.flost.Models;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Message {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
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
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

//    public int getType() {
//        return mType;
//    };

    public String getMessage() {
        return mMessage;
    };

    public String getSender() {
        return mSender.getUserID();
    };

    public long getCreatedAt() {

    }


//    public static class Builder {
//        private final int mType;
//        private String mUsername;
//        private String mMessage;
//
//        public Builder(int type) {
//            mType = type;
//        }
//
//        public Builder username(String username) {
//            mUsername = username;
//            return this;
//        }
//
//        public Builder message(String message) {
//            mMessage = message;
//            return this;
//        }
//
//        public Message build() {
//            Message message = new Message();
//            message.mType = mType;
//            message.mUsername = mUsername;
//            message.mMessage = mMessage;
//            return message;
//        }
//    }
}
