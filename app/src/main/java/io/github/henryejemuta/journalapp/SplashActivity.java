package io.github.henryejemuta.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import io.github.henryejemuta.journalapp.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                closeSplash();
            }
        }, 5000);//Close after 5sec
    }

    private void closeSplash(){
        startActivity(new Intent(this, LoginActivity.class));
        SplashActivity.this.finish();
    }
}
