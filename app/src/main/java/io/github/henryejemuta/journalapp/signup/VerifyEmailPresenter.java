package io.github.henryejemuta.journalapp.signup;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.henryejemuta.journalapp.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class VerifyEmailPresenter implements VerifyEmailContract.UserActionsListener, View.OnClickListener {
    private static final String LOG_TAG = VerifyEmailPresenter.class.getSimpleName();

    @NonNull
    private final VerifyEmailContract.View mVerifyEmailView;
    @NonNull
    private final FirebaseAuth mFirebaseAuth;


    public VerifyEmailPresenter(@NonNull FirebaseAuth mFirebaseAuth, @NonNull VerifyEmailContract.View mVerifyEmailView) {
        this.mFirebaseAuth = checkNotNull(mFirebaseAuth);
        this.mVerifyEmailView = checkNotNull(mVerifyEmailView);
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return this;
    }


    @Override
    public void updateUI(FirebaseUser user) {
        if (user != null) {
            mVerifyEmailView.showUserStatus(user.getEmail() + " ( " + (user.isEmailVerified() ? "Verified!" : "Unverified!") + " )");
            if (user.isEmailVerified())
                mVerifyEmailView.showJournals();
        } else {
            FirebaseAuth.getInstance().signOut();
        }
    }


    private void sendEmailVerification() {
        // Disable button
        mVerifyEmailView.setVerifyButtonEnable(false);
        try {
            final FirebaseUser user = mFirebaseAuth.getCurrentUser();
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mVerifyEmailView.setVerifyButtonEnable(true);

                    if (task.isSuccessful()) {
                        mVerifyEmailView.showEmailStatus("Verification email successfully sent to " + user.getEmail());
                    } else {
                        Log.e(LOG_TAG, "sendEmailVerification", task.getException());
                        mVerifyEmailView.showEmailStatus("Verification email sending failed " + user.getEmail());
                    }
                }
            });
        } catch (NullPointerException e) {
            Log.d(LOG_TAG, e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.btn_v_sign_out:
                mVerifyEmailView.signOut();
                break;
            case R.id.btn_v_verify_email:
                sendEmailVerification();
                break;
            default:
                ;
                break;
        }
    }
}
