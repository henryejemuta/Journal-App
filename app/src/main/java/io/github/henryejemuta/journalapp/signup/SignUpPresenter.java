package io.github.henryejemuta.journalapp.signup;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

public class SignUpPresenter implements SignUpFragmentContract.UserActionsListener, View.OnClickListener {
    private static final String LOG_TAG = SignUpPresenter.class.getSimpleName();

    @NonNull
    private final SignUpFragmentContract.View mSignUpView;
    @NonNull
    private final FirebaseAuth mFirebaseAuth;


    public SignUpPresenter(@NonNull FirebaseAuth mFirebaseAuth, @NonNull SignUpFragmentContract.View mSignUpView) {
        this.mFirebaseAuth = checkNotNull(mFirebaseAuth);
        this.mSignUpView = checkNotNull(mSignUpView);
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return this;
    }

    @Override
    public void signUpWithEmailAndPassword(String userEmail, String userPassword) {
        if (validateUserInputs(userEmail, userPassword)) return;

        mFirebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
            mSignUpView.showSignUpError("Authentication Failed.");
            updateUI(null);
        }
    }

    @Override
    public void updateUI(FirebaseUser user) {
        if (user != null){
            mSignUpView.showVerifyEmailFragment();
        }else{
            FirebaseAuth.getInstance().signOut();
        }
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.btn_s_sign_up:  mSignUpView.signUp();
                break;
            case R.id.tv_s_forgot_password:  mSignUpView.showForgotPassword();
                break;
            case R.id.tv_s_login:  mSignUpView.showLogin();
                break;
            default: ;
                break;
        }
    }

    private boolean validateUserInputs(String userEmail, String userPassword) {
        if(userEmail.trim().isEmpty()){
            mSignUpView.showEmailError("Email field MUST NOT be empty!");
            return true;
        }else if (!Utils.isValidEmail(userEmail.trim())){
            mSignUpView.showEmailError("Invalid email format!");
            return true;
        }else {
            mSignUpView.showEmailError(null);
        }


        if(userPassword.trim().isEmpty()){
            mSignUpView.showPasswordError("Password field MUST NOT be empty!");
            return true;
        }else if (userPassword.trim().length() < 6){
            mSignUpView.showPasswordError("Password MUST be Six(6) Characters and above!");
            return true;
        }else {
            mSignUpView.showPasswordError(null);
        }
        return false;
    }
}
