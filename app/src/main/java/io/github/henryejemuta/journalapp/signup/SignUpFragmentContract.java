package io.github.henryejemuta.journalapp.signup;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface SignUpFragmentContract {
    interface View{

        void showEmailError(String msg);

        void showPasswordError(String msg);

        void signUp();

        void showSignUpError(String message);

        void showLoggingIn();

        void showVerifyEmailFragment();

        void showLogin();

        void showForgotPassword();

        void setFragmentActionsListener(FragmentActionsListener fragmentActions);

    }

    interface UserActionsListener {
        android.view.View.OnClickListener getOnClickListener();

        void signUpWithEmailAndPassword(String userEmail, String userPassword);

        void checkLoginSuccess(Task<AuthResult> task);

        void updateUI(FirebaseUser user);
    }

    interface FragmentActionsListener {
        void navigateToActivity(Class<?> cls);

        void navigateToActivity(Class<?> cls, boolean shouldFinishThis);

        void switchToVerifyFragment();
    }
}
