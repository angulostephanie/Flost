package com.specialtopics.flost.Views;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.d("UserID", mUser.getUid());

        emailTextView = findViewById(R.id.emailText);
        logoutBtn = findViewById(R.id.logoutBtn);

        emailTextView.setText("Hello " + mUser.getDisplayName());
        logoutBtn.setOnClickListener(v -> logout());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_messages:
                    //toolbar.setTitle("");
                    fragment = new MessagesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    //toolbar.setTitle("");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
