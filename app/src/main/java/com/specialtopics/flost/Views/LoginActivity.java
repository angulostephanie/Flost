package com.specialtopics.flost.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.specialtopics.flost.Controllers.ChatApplication;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.R;

import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 101;

    // entry point of Firebase Auth SDK
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Context mContext;
    private SignInButton loginBtn;
    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(v -> {
            login();
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Update UI accordingly based on if user is already signed in (non-null)
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void login() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void updateUI() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            Log.d(TAG,"HELLO THERE " + user.getEmail());
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "No user signed in");
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        FlostRestClient.addUserToDB(mContext, user);
                        updateUI();
                        mSocket.on("login", onLogin);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(findViewById(R.id.login_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                    }

                    // ...
                });
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
            setResult(RESULT_OK, intent);
            finish();
        }
    };




        /*
        String uid = user.getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("users").child(uid);

        userNameRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //create new user
                    Log.d(TAG, "Adding this user to the database :)!");
                    String first = "";
                    if(user.getDisplayName() != null) {
                        first = user.getDisplayName().split(" ")[0];
                    }

                    String email = user.getEmail();
                    String uid = user.getUid();

                    User dbUser = new User(uid, email, first);
                    Map<String, Object> userValues = dbUser.toMap();

                    // this line adds the user to the db's json tree
                    userNameRef.updateChildren(userValues);

                } else {
                    Log.d(TAG, "User already exists!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        });
    */

}
