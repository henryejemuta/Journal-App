package io.github.henryejemuta.journalapp.resetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.login.LoginActivity;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordContract.View {
    private ResetPasswordContract.UserActionsListener mActionListener;

    private EditText mUserEmail;
    private LinearLayout mEmailContainer;
    private TextView mStatus;
    private TextView mLoginLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mActionListener = new ResetPasswordPresenter(this);

        mEmailContainer = findViewById(R.id.ll_r_email_container);
        mUserEmail = findViewById(R.id.et_r_email);
        mStatus = findViewById(R.id.tv_r_status);
        mLoginLink = findViewById(R.id.tv_r_login);

        Button resetEmail = findViewById(R.id.btn_r_reset_password);

        resetEmail.setOnClickListener(mActionListener.getOnClickListener());
        mLoginLink.setOnClickListener(mActionListener.getOnClickListener());
    }

    @Override
    public void showEmailStatus(String status) {
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void sendResetEmail() {
        mActionListener.sendPasswordResetEmail(mUserEmail.getText().toString());
    }

    @Override
    public void showEmailError(String msg) {
        mUserEmail.setError(msg);
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        ResetPasswordActivity.this.finish();
    }
}
