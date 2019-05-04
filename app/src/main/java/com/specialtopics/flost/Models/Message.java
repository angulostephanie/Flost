package com.specialtopics.flost.Models;
public class Message {

    String mUsername;
    String mMessage;
    String mReceiverEmail;
    String mSenderEmail;
    User mReceiver; // I don't think we should have the entire User obj, probably just having their email is fine.
    User mSender;
    long createdAt;
    String timestamp;
    int id;
//    public static final int TYPE_MESSAGE = 0;
//    public static final int TYPE_LOG = 1;
//    public static final int TYPE_ACTION = 2;
//
//    private int mType;
//    private String mMessage;
//    private String mUsername;
//
    private Message() {
        id = this.hashCode();
    }

    // This constructor is needed for when the REST client fetches messages
    public Message(int id, String receiverEmail, String senderEmail, String messageContent, String timestamp) {
        this.id = id;
        this.mReceiverEmail = receiverEmail;
        this.mSenderEmail = senderEmail;
        this.mMessage = messageContent;
        this.timestamp = timestamp;

    }

//    public int getType() {
//        return mType;
//    };

    public String getMessage() {
        return mMessage;
    }

    public String getSender() {
        return mSenderEmail;

    }

    public String getReceiver() {
        return mReceiverEmail;
    }

    public int getID() {
        return id;
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
