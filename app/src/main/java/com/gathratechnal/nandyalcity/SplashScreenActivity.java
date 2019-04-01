package com.gathratechnal.nandyalcity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gathratechnal.nandyalcity.deal.DealActivity;
import com.gathratechnal.nandyalcity.login.LoginActivity;
import com.gathratechnal.nandyalcity.utils.Preferences;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler = null;
    private Runnable splashTimer = new Runnable() {
        @Override
        public void run() {

            if (new Preferences().getUserName(SplashScreenActivity.this).isEmpty()) {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            } else {
                startActivity(new Intent(SplashScreenActivity.this, DealActivity.class));
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

    }

    @Override
    protected void onStart() {
        super.onStart();

        handler = new Handler();
        handler.postDelayed(splashTimer, 2000);   //2seconds

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
