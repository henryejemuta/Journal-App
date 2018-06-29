package io.github.henryejemuta.journalapp.resetpassword;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

public class ResetPasswordPresenter implements ResetPasswordContract.UserActionsListener, View.OnClickListener {
    private static final String LOG_TAG = ResetPasswordPresenter.class.getSimpleName();

    @NonNull
    private final ResetPasswordContract.View mResetPasswordView;

    public ResetPasswordPresenter(@NonNull ResetPasswordContract.View mResetPasswordView) {
        this.mResetPasswordView = checkNotNull(mResetPasswordView);
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return this;
    }

    @Override
    public void sendPasswordResetEmail(String emailAddress) {
        if (validateUserInputs(emailAddress)) return;

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mResetPasswordView.showEmailStatus("Verification Email Sent!");
                            Log.d(LOG_TAG, "Verification Email Sent!");
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.btn_r_reset_password:
                mResetPasswordView.sendResetEmail();
                break;
            case R.id.tv_r_login:
                mResetPasswordView.showLogin();
                break;
            default:
                ;
                break;
        }
    }


    private boolean validateUserInputs(String userEmail) {
        if (userEmail.trim().isEmpty()) {
            mResetPasswordView.showEmailError("Email field MUST NOT be empty!");
            return true;
        } else if (!Utils.isValidEmail(userEmail.trim())) {
            mResetPasswordView.showEmailError("Invalid email format!");
            return true;
        } else {
            mResetPasswordView.showEmailError(null);
        }

        return false;
    }
}
