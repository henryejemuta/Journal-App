package io.github.henryejemuta.journalapp.login;


import android.content.Intent;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * This specifies the contract between the loginWithEmailAndPassword view and it's presenter.
 */
public interface LoginContract {
    interface View {

        void showEmailError(String msg);

        void showPasswordError(String msg);

        void signIn();

        void signInWithGoogle();

        void showLoginError(String message);

        void showConnectivityError();

        void showLoggingIn();

        void showJournals();

        void showSignUp();

        void showForgotPassword();

        void showVerifyEmail();
    }

    interface UserActionsListener {

        android.view.View.OnClickListener getOnClickListener();

        void loginWithEmailAndPassword(String userEmail, String userPassword);

        void checkLoginSuccess(Task<AuthResult> task);

        void checkGoogleLoginResult(int requestCode, int resultCode, Intent data);

        void updateUI(FirebaseUser user);
    }
}
