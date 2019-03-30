package com.specialtopics.flost.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.specialtopics.flost.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Context mContext;
    private Button logoutBtn;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        emailTextView = findViewById(R.id.emailText);
        logoutBtn = findViewById(R.id.logoutBtn);

        emailTextView.setText("Hello " + mUser.getDisplayName());
        logoutBtn.setOnClickListener(v -> logout());
    }

    private void logout() {
        // mUser becomes null after this.
        mAuth.signOut();
        updateUI();
    }
    private void updateUI() {
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        startActivity(loginIntent);
    }
}
