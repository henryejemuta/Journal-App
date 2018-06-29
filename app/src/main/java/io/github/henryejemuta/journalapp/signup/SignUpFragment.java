package io.github.henryejemuta.journalapp.signup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.login.LoginActivity;
import io.github.henryejemuta.journalapp.resetpassword.ResetPasswordActivity;

public class SignUpFragment extends Fragment implements SignUpFragmentContract.View {
    private SignUpFragmentContract.UserActionsListener mActionListener;
    private SignUpFragmentContract.FragmentActionsListener mFragmentActionsListener;

    //    Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private EditText mUserEmail;
    private EditText mPassword;

    private View mViewRoot;


    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mActionListener = new SignUpPresenter(mFirebaseAuth, this);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mActionListener.updateUI(firebaseAuth.getCurrentUser());
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewRoot = inflater.inflate(R.layout.signup_view, container, false);


        mUserEmail = mViewRoot.findViewById(R.id.et_s_login_user_id);
        mPassword = mViewRoot.findViewById(R.id.et_s_login_password);

        Button signUpButton = mViewRoot.findViewById(R.id.btn_s_sign_up);
        TextView forgotPasswordLink = mViewRoot.findViewById(R.id.tv_s_forgot_password);
        TextView loginLink = mViewRoot.findViewById(R.id.tv_s_login);


        signUpButton.setOnClickListener(mActionListener.getOnClickListener());
        forgotPasswordLink.setOnClickListener(mActionListener.getOnClickListener());
        loginLink.setOnClickListener(mActionListener.getOnClickListener());


//        setRetainInstance(true);
        return mViewRoot;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mActionListener.updateUI(mFirebaseAuth.getCurrentUser());
    }

    @Override
    public void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onResume() {
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
    public void signUp() {
        mActionListener.signUpWithEmailAndPassword(mUserEmail.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void showSignUpError(String msg) {
        Snackbar.make(mViewRoot.findViewById(R.id.iv_s_app_logo), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoggingIn() {

    }

    @Override
    public void showVerifyEmailFragment() {
        mFragmentActionsListener.switchToVerifyFragment();
    }

    @Override
    public void showLogin() {
        mFragmentActionsListener.navigateToActivity(LoginActivity.class, true);
    }

    @Override
    public void showForgotPassword() {
        mFragmentActionsListener.navigateToActivity(ResetPasswordActivity.class);
    }

    @Override
    public void setFragmentActionsListener(SignUpFragmentContract.FragmentActionsListener fragmentActions) {
        this.mFragmentActionsListener = fragmentActions;
    }
}
