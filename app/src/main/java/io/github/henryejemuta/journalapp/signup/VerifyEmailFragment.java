package io.github.henryejemuta.journalapp.signup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.journals.JournalsActivity;
import io.github.henryejemuta.journalapp.login.LoginActivity;

public class VerifyEmailFragment extends Fragment implements VerifyEmailContract.View{
    private SignUpFragmentContract.FragmentActionsListener mFragmentActionsListener;
    private VerifyEmailContract.UserActionsListener mActionListener;


    //    Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private TextView mEmailVerificationStatus;
    private Button mVerifyEmailButton;


    public static VerifyEmailFragment newInstance() {
        return new VerifyEmailFragment();
    }

    public VerifyEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mActionListener = new VerifyEmailPresenter(mFirebaseAuth, this);

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
        View rootView = inflater.inflate(R.layout.fragment_verify_email, container, false);


        mEmailVerificationStatus = rootView.findViewById(R.id.tv_v_status);
        Button logOutButton = rootView.findViewById(R.id.btn_v_sign_out);
        mVerifyEmailButton = rootView.findViewById(R.id.btn_v_verify_email);

        logOutButton.setOnClickListener(mActionListener.getOnClickListener());
        mVerifyEmailButton.setOnClickListener(mActionListener.getOnClickListener());


//        setRetainInstance(true);
        return rootView;
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
    public void setVerifyButtonEnable(boolean b) {
        mVerifyEmailButton.setEnabled(b);
    }

    @Override
    public void showEmailStatus(String status) {
        Toast.makeText(this.getActivity(), status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void signOut() {
        mFragmentActionsListener.navigateToActivity(LoginActivity.class, true);
    }

    @Override
    public void setFragmentActionsListener(SignUpFragmentContract.FragmentActionsListener fragmentActions) {
        mFragmentActionsListener = fragmentActions;
    }

    @Override
    public void showJournals() {
        mFragmentActionsListener.navigateToActivity(JournalsActivity.class, true);
    }

    @Override
    public void showUserStatus(String status) {
        mEmailVerificationStatus.setText(status);
    }
}
