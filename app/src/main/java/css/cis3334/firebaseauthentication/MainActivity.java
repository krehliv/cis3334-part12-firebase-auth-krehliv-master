package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * If the Google account connection fails, display a toast that says "fail".
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Log.w("CIS3334", "onConnectionFailed:failed");
        //Toast.makeText(MainActivity.this, "FAIL", Toast.LENGTH_LONG).show();
        textViewStatus.setText("Google Account Fail");
    }

    /**
     * Start authentication.
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Stop authentication
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d("CIS334", "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("CIS334", "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w("CIS334", "signInWithCredential", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // ...
//                    }
//                });
//    }

    /**
     * Set up all the button values etc.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //if (user != null) {
                    // User is signed in
                    //Log.d("CIS3334", "onAuthStateChanged:signed_in:" + user.getUid());
                //} else {
                    // User is signed out
                    //Log.d("CIS3334", "onAuthStateChanged:signed_out");
                //}
                // ...
            }
        };

        /**
         * Grabs the text from editTextEmail and editTextPassword when the "normal login" button is clicked.
         */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "normal login ");
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        /**
         * Grabs the editTextEmail and exitTextPassword text to create a new account.
         */
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Create Account ");
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

//        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.d("CIS3334", "Google login ");
//                googleSignIn();
//            }
//        });

        /**
         * Signs out the current user.
         */
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Logging out - signOut ");
                signOut();
            }
        });

        //private GoogleApiClient mGoogleApiClient;
        //private static final int GOOGLE_SIGN_IN_FLAG = 9001;

        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        // Build a GoogleApiClient with access to the Google Sign-In API and the
//        // options specified by gso.
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

    }

    /**
     * Creates an account using the email and password strings acquired from the Create Account button action.
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("CIS3334", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //if (!task.isSuccessful()) {
                        //    Toast.makeText(MainActivity.this, "FAIL",
                        //            Toast.LENGTH_SHORT).show();
                        //}

                        // ...
                    }
                });
    }

    /**
     * Signs in an existing user using email and password acquired from text fields in the UI.
     * @param email
     * @param password
     */
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("CIS3334", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //if (!task.isSuccessful()) {
                        //    Log.w("CIS3334", "signInWithEmail:failed", task.getException());
                        //    Toast.makeText(MainActivity.this, "FAIL",
                        //            Toast.LENGTH_SHORT).show();
                        //}

                        // ...
                    }
                });
    }

    /**
     * Sets the user's name, email, photoUrl, and firebase User ID.
     */
    private void getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }

    /**
     * Sign out the current user.
     */
    private void signOut () {
        mAuth.signOut();
    }

//    private void googleSignIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_FLAG);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == GOOGLE_SIGN_IN_FLAG) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = result.getSignInAccount();
//                firebaseAuthWithGoogle(account);
//            } else {
//                // Google Sign In failed, update UI appropriately
//                // ...
//            }
//        }
//    }




}
