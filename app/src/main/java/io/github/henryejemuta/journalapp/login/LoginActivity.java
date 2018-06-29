package io.github.henryejemuta.journalapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.resetpassword.ResetPasswordActivity;
import io.github.henryejemuta.journalapp.journals.JournalsActivity;
import io.github.henryejemuta.journalapp.signup.SignUpActivity;
import io.github.henryejemuta.journalapp.util.Constants;

import static io.github.henryejemuta.journalapp.util.Constants.GOOGLE_SIGNIN_WEB_CLIENT_ID;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    static final int RC_SIGN_IN = 1;

    private LoginContract.UserActionsListener mActionListener;

    //    Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private GoogleSignInClient mGoogleSignInClient;


    private EditText mUserEmail;
    private EditText mPassword;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        mFirebaseAuth = FirebaseAuth.getInstance();
        initGoogleSignInClient();
        mActionListener = new LoginPresenter(mFirebaseAuth, this);

        mUserEmail = findViewById(R.id.et_l_login_user_id);
        mPassword = findViewById(R.id.et_l_login_password);

        Button loginButton = findViewById(R.id.btn_l_login);
        Button loginWithGoogleButton = findViewById(R.id.btn_l_google_login);
        TextView forgotPasswordLink = findViewById(R.id.tv_l_forgot_password);
        TextView signUpLink = findViewById(R.id.tv_l_sign_up);


        loginButton.setOnClickListener(mActionListener.getOnClickListener());
        loginWithGoogleButton.setOnClickListener(mActionListener.getOnClickListener());
        forgotPasswordLink.setOnClickListener(mActionListener.getOnClickListener());
        signUpLink.setOnClickListener(mActionListener.getOnClickListener());


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mActionListener.updateUI(firebaseAuth.getCurrentUser());
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mActionListener.updateUI(mFirebaseAuth.getCurrentUser());
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_SIGNIN_WEB_CLIENT_ID)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        mActionListener.checkGoogleLoginResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    public void showEmailError(String msg) {
        mUserEmail.setError(msg);
    }

    @Override
    public void showPasswordError(String msg) {
        mPassword.setError(msg);
    }

    @Override
    public void signIn() {
        mActionListener.loginWithEmailAndPassword(mUserEmail.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void showLoginError(String msg) {
        Snackbar.make(findViewById(R.id.iv_l_app_logo), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectivityError() {

    }

    @Override
    public void showLoggingIn() {

    }

    @Override
    public void showJournals() {
        startActivity(new Intent(this, JournalsActivity.class));
        LoginActivity.this.finish();
    }

    @Override
    public void showSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        LoginActivity.this.finish();
    }

    @Override
    public void showForgotPassword() {
        startActivity(new Intent(this, ResetPasswordActivity.class));
        LoginActivity.this.finish();
    }

    @Override
    public void showVerifyEmail() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra(Constants.SIGN_UP_ACTIVITY_VIEW_KEY, Constants.VERIFY_FRAGMENT);
        startActivity(intent);
    }
}
