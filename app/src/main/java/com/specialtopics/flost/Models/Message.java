package com.specialtopics.flost.Models;

import java.util.Objects;

public class Message {

    private String mMessage;
    private String mReceiverEmail;
    private String mSenderEmail;
    private long createdAt;
    private String timestamp;
    private int messageId;
    private int chatroomId;
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
    public Message(Integer chatroomId, Integer messageId, String timestamp, String senderEmail, String receiverEmail, String messageContent) {
        this.chatroomId = chatroomId;
        this.messageId = messageId;
        this.mSenderEmail = senderEmail;
        this.mReceiverEmail = receiverEmail;
        this.mMessage = messageContent;
        this.timestamp = timestamp;
    }

    // This constructor is needed for when the REST client pushes messages
    public Message(Integer chatroomId, String senderEmail, String receiverEmail, String messageContent) {
        this.chatroomId = chatroomId;
        this.messageId = this.hashCode();
        this.mSenderEmail = senderEmail;
        this.mReceiverEmail = receiverEmail;
        this.mMessage = messageContent;
        this.timestamp = String.valueOf(this.getCreatedAt());

    }

//    public int getType() {
//        return mType;
//    };


//    public String getSenderName() {
//        return mSenderName;
//    }

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


    public String getReceiverEmail() {
        return mReceiverEmail;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public int getMessageId() {
        return messageId;
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
