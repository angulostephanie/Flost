package com.specialtopics.flost.Views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.specialtopics.flost.Controllers.ChatApplication;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.User;
import com.specialtopics.flost.R;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
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
        // updateUI();
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
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Authenticating...");
        progressDialog.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        Log.d(TAG, "token: " + acct.getIdToken());
                        FlostRestClient.authenticateUser(mContext, acct.getIdToken(), new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Log.d(TAG, "Adding this user to the mysql :)!");
                                User verifiedUser = null;
                                try {
                                    verifiedUser = new User();
                                    verifiedUser.setEmail(response.getString("email"));
                                    verifiedUser.setFirst(response.getString("first_name"));
                                    verifiedUser.setLast(response.getString("last_name"));
                                    if(response.has("photo_url")) verifiedUser.setPhotoURL(response.getString("photo_URL"));
                                } catch(JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d(TAG, "Updating the UI now :)");
                                progressDialog.dismiss();
                                updateUI();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                if(errorResponse != null) Log.d(TAG, errorResponse.toString());
                                Log.d(TAG, "faileddddd!");
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Failed at authenticating :/ ", Toast.LENGTH_SHORT).show();
                                // TODO: add a toast or snack bar please
                            }

                            @Override
                            public void onProgress(long bytesWritten, long totalSize) {
                                // TODO: add a progress bar animation here! :)
                                double progress = (100.0*bytesWritten)/totalSize;
                                Log.d("Progress", String.valueOf(progress));
                            }
                        });

                        mSocket.on("login", onLogin);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        progressDialog.dismiss();
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

}
