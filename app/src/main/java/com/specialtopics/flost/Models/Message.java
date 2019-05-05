package com.specialtopics.flost.Models;

import java.util.Objects;

public class Message {

    private String mUsername;
    private String mMessage;
    String mReceiverEmail;
    String mReceiverName;
    private String mSenderName;
    private String mSenderEmail;
//    private User mReceiver; // I don't think we should have the entire User obj, probably just having their email is fine.
//    private User mSender;
    private long createdAt;
    private String timestamp;
    private int id;
//    public static final int TYPE_MESSAGE = 0;
//    public static final int TYPE_LOG = 1;
//    public static final int TYPE_ACTION = 2;
//
//    private int mType;
//    private String mMessage;
//    private String mUsername;
//
//    private Message() {
//        id = this.hashCode();
//    }

    // This constructor is needed for when the REST client fetches messages
    public Message(Integer id, String timestamp, String senderName, String senderEmail, String messageContent) {
        this.id = id;
        this.timestamp = timestamp;
        this.mSenderName = senderName;
        this.mSenderEmail = senderEmail;
        this.mMessage = messageContent;
        //        this.mReceiverName = receiverName;
//        this.mReceiverEmail = receiverEmail;
    }

    // This constructor is needed for when the REST client pushes messages
    public Message(String senderName, String senderEmail, String messageContent) {
        id = this.hashCode();
        this.timestamp = String.valueOf(this.getCreatedAt());
        this.mSenderName = senderName;
        this.mSenderEmail = senderEmail;
        this.mMessage = messageContent;
        //        this.mReceiverName = receiverName;
//        this.mReceiverEmail = receiverEmail;
    }

//    public int getType() {
//        return mType;
//    };


    public String getSenderName() {
        return mSenderName;
    }

//    public String getReceiverName() {
//        return mReceiverName;
//    }

    public String getMessage() {
        return mMessage;
    }

    public String getSenderEmail() {
        return mSenderEmail;

    }

//    public String getReceiver() {
//        return mReceiverEmail;
//    }

    public int getID() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt = System.currentTimeMillis();
    }


//    public static class Builder {
//        private String mUsername;
//        private String mMessage;
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
//            message.mUsername = mUsername;
//            message.mMessage = mMessage;
//            return message;
//        }
//    }

    public int hashCode() {
        return Math.abs(Objects.hash(mReceiverEmail, mSenderEmail, mMessage, createdAt));
    }

}
