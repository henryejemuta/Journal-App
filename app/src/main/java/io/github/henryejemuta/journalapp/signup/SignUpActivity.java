package io.github.henryejemuta.journalapp.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.util.Constants;
import io.github.henryejemuta.journalapp.util.EspressoIdlingResource;

public class SignUpActivity extends AppCompatActivity implements SignUpFragmentContract.FragmentActionsListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = getIntent();
        int viewID = intent.getIntExtra(Constants.SIGN_UP_ACTIVITY_VIEW_KEY, Constants.SIGN_UP_FRAGMENT);

        if (null == savedInstanceState && (viewID != Constants.VERIFY_FRAGMENT)) {
            switchToSignUpFragment();
        }else {
            switchToVerifyFragment();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initFragment(Fragment detailFragment) {
        // Add the SignUpFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_as_contentFrame, detailFragment);
        transaction.commit();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    public void navigateToActivity(Class<?> cls) {
        navigateToActivity(cls, false);
    }

    @Override
    public void navigateToActivity(Class<?> cls, boolean shouldFinishThis) {
        try {
            startActivity(new Intent(this, cls));
            if (shouldFinishThis)
                this.finish();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SignUpActivity.class.getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public void switchToVerifyFragment() {
        VerifyEmailFragment verifyEmailFragment = VerifyEmailFragment.newInstance();
        verifyEmailFragment.setFragmentActionsListener(this);
        initFragment(verifyEmailFragment);
    }

    public void switchToSignUpFragment() {
        SignUpFragment signUpFragment = SignUpFragment.newInstance();
        signUpFragment.setFragmentActionsListener(this);
        initFragment(signUpFragment);
    }
}
