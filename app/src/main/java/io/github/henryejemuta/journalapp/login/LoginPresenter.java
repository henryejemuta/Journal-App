package io.github.henryejemuta.journalapp.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.github.henryejemuta.journalapp.login.LoginActivity.RC_SIGN_IN;

/**
 * LoginPresenter listens to user actions from the loginWithEmailAndPassword UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
public class LoginPresenter implements LoginContract.UserActionsListener, View.OnClickListener {
    private static final String LOG_TAG = LoginPresenter.class.getSimpleName();

    @NonNull
    private final LoginContract.View mLoginView;
    @NonNull
    private final FirebaseAuth mFirebaseAuth;


    public LoginPresenter(@NonNull FirebaseAuth mFirebaseAuth, @NonNull LoginContract.View mLoginView) {
        this.mFirebaseAuth = checkNotNull(mFirebaseAuth);
        this.mLoginView = checkNotNull(mLoginView);
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return this;
    }

    @Override
    public void loginWithEmailAndPassword(String userEmail, String userPassword) {
        if (validateUserInputs(userEmail, userPassword)) return;


        mFirebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                checkLoginSuccess(task);
            }
        });
    }

    @Override
    public void checkLoginSuccess(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(LOG_TAG, "signInWithCredential:success");
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            updateUI(user);
        } else {
            // If sign in fails, display a message to the user.
            Log.w(LOG_TAG, "signInWithCredential:failure", task.getException());
            mLoginView.showLoginError("Invalid Login Details\n\rAuthentication Failed.");
            updateUI(null);
        }
    }

    @Override
    public void checkGoogleLoginResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                String msg = "Google sign in failed";
                Log.w(LOG_TAG, msg, e);
                mLoginView.showLoginError(msg);
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(LOG_TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                checkLoginSuccess(task);
            }
        });
    }

    @Override
    public void updateUI(FirebaseUser user) {
        if (user != null){
            if(!user.isEmailVerified()){
                mLoginView.showVerifyEmail();
            }else {
                mLoginView.showJournals();
            }
        }else{
            FirebaseAuth.getInstance().signOut();
        }
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.btn_l_login:  mLoginView.signIn();
                break;
            case R.id.btn_l_google_login:  mLoginView.signInWithGoogle();
                break;
            case R.id.tv_l_forgot_password:  mLoginView.showForgotPassword();
                break;
            case R.id.tv_l_sign_up:  mLoginView.showSignUp();
                break;
            default: ;
                break;
        }
    }

    private boolean validateUserInputs(String userEmail, String userPassword) {
        if(userEmail.trim().isEmpty()){
            mLoginView.showEmailError("Email field MUST NOT be empty!");
            return true;
        }else if (!Utils.isValidEmail(userEmail.trim())){
            mLoginView.showEmailError("Invalid email format!");
            return true;
        }else {
            mLoginView.showEmailError(null);
        }


        if(userPassword.trim().isEmpty()){
            mLoginView.showPasswordError("Password field MUST NOT be empty!");
            return true;
        }else if (userPassword.trim().length() < 6){
            mLoginView.showPasswordError("Password MUST be Six(6) Characters and above!");
            return true;
        }else {
            mLoginView.showPasswordError(null);
        }
        return false;
    }
}
