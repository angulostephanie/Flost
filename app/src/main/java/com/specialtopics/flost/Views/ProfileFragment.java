package com.specialtopics.flost.Views;


import android.content.Context;
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
import com.specialtopics.flost.R;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Context mContext;
    private Button logoutBtn;
    private TextView emailTextView;

    public ProfileFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.d("UserID", mUser.getUid());

        emailTextView = view.findViewById(R.id.emailText);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        emailTextView.setText("Hello " + mUser.getDisplayName());

        return view;
    }

}
