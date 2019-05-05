package com.specialtopics.flost.Models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Controllers.DateUtils;
import com.specialtopics.flost.R;

import java.util.List;


public class MessageListAdapter extends RecyclerView.Adapter {
    private static final String TAG = "Adapter";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<Message> mMessageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mContext = context;
        mMessageList = messageList;
    }

    public List<Message> getmMessageList(){
        return this.mMessageList;
    }

    @Override
    public int getItemCount() {
        String temp = "";
        for(int i = 0; i < mMessageList.size(); i++){
            temp = temp + mMessageList.get(i).getMessage();
        }
        Log.d(TAG, temp);
        Log.d(TAG, "messagelist size: " + mMessageList.size());

        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {

        Log.e(TAG, "getItemViewType");


        Message message = (Message) mMessageList.get(position);

        if (message.getSenderName().equals(mUser.getDisplayName())) {
            Log.e(TAG, "sent message returned.");
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            Log.e(TAG, "received message returned.");
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        Log.e(TAG, "onCreateViewHolder");


        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) mMessageList.get(position);
        Log.e(TAG, "in onBindViewHolder.");


        switch (getItemViewType(position)) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }

//        switch (holder.getItemViewType()) {
//            case VIEW_TYPE_MESSAGE_SENT:
//                ((SentMessageHolder) holder).bind(message);
//                break;
//            case VIEW_TYPE_MESSAGE_RECEIVED:
//                ((ReceivedMessageHolder) holder).bind(message);
//        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.sent_message_body);
            timeText = (TextView) itemView.findViewById(R.id.sent_message_time);
        }

        void bind(Message message) {

            Log.e(TAG, "binding SentMessageHolder");

            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.received_message_body);
            timeText = (TextView) itemView.findViewById(R.id.received_message_time);
            nameText = (TextView) itemView.findViewById(R.id.received_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(Message message) {

            Log.e(TAG, "binding ReceivedMessageHolder");


            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));

            nameText.setText(message.getSenderName());

            // TODO Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }
}
