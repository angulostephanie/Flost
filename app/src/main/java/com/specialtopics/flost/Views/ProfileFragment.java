package com.specialtopics.flost.Views;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.Controllers.ChatApplication;
import com.specialtopics.flost.R;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Context mContext;
    private Button logoutBtn;
    private TextView emailTextView;
    private Socket mSocket;

    public ProfileFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getContext();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.d("UserID", mUser.getUid());

        emailTextView = view.findViewById(R.id.emailText);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> logout());
        emailTextView.setText("Hello " + mUser.getDisplayName());

        return view;
    }

    private void logout() {
        // mUser becomes null after this.
        mAuth.signOut();
        updateUI();
        mSocket.off("login", onLogin);
    }
    private void updateUI() {
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        startActivity(loginIntent);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
//            JSONObject data = (JSONObject) args[0];

//            int numUsers;
//            try {
//                numUsers = data.getInt("numUsers");
//            } catch (JSONException e) {
//                return;
//            }

            Intent intent = new Intent();
            intent.putExtra("username", mAuth.getCurrentUser().getDisplayName());
//            intent.putExtra("numUsers", numUsers);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        }
    };

}
