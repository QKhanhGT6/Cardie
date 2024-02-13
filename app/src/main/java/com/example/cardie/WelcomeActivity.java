package com.example.cardie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class WelcomeActivity extends Activity {
    int count;
    int c=0;

    @Override
    public void onBackPressed() {
        boolean flag = false;
        if (flag) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseFunctions connect = new DatabaseFunctions(WelcomeActivity.this);
        try{
            connect.AddData();
        } catch (Exception e){
        }
//        SharedPreferences sharedPref
//                = getSharedPreferences("UserProfile",
//                MODE_PRIVATE);
//        int isFirstTime = sharedPref.getInt("first-time",0);
//        int isFirstTime=0;
//        System.out.println(isFirstTime);
        if (true){
            setContentView(R.layout.welcome_layout);
            final TextView touchhere = findViewById(R.id.welcome_touchhere);
            final TextView description = findViewById(R.id.welcome_text);
            final LottieAnimationView splash = findViewById(R.id.welcome_splash);
            final LottieAnimationView tired = findViewById(R.id.welcome_writedown);
            final LottieAnimationView gotyou = findViewById(R.id.welcome_gotyou);
            final LottieAnimationView card = findViewById(R.id.welcome_cardie);
            final LottieAnimationView start = findViewById(R.id.welcome_start);
            tired.setVisibility(View.GONE);
            gotyou.setVisibility(View.GONE);
            card.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
            if (count==0){
                final MediaPlayer mp = MediaPlayer.create(this, R.raw.welcome);
                splash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        splash.setSpeed(2);
                        splash.playAnimation();
                        splash.setEnabled(false);
                        splash.setClickable(false);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                touchhere.setText("");
                                tired.setVisibility(View.VISIBLE);
                                tired.setSpeed(2);
                                tired.playAnimation();
                                tired.setRepeatCount(9999);
                                description.setText("Tired of having to write down whenever you wanna remember something?");
                                tired.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                tired.setVisibility(View.GONE);
                                                gotyou.setVisibility(View.VISIBLE);
                                                gotyou.setSpeed(2);
                                                gotyou.playAnimation();

                                                gotyou.setRepeatCount(9999);
                                                description.setText("Don't worry, we've got you covered");
                                                gotyou.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                gotyou.setVisibility(View.GONE);
                                                                gotyou.setEnabled(false);
                                                                gotyou.setClickable(false);
                                                                card.setVisibility(View.VISIBLE);
                                                                card.setSpeed(1);
                                                                card.playAnimation();

                                                                card.setRepeatCount(9999);
                                                                description.setText("Our Cardie app provides you with intensive flash cards system");
                                                                card.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        card.setVisibility(View.GONE);
                                                                        card.setEnabled(false);
                                                                        card.setClickable(false);
                                                                        start.setVisibility(View.VISIBLE);
                                                                        description.setText("Let's get started");
                                                                        start.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                mp.start();
                                                                                start.setSpeed(1);
                                                                                start.playAnimation();
                                                                                start.setEnabled(false);
                                                                                start.setClickable(false);
                                                                                handler.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                                                                                        startActivity(intent);
                                                                                    }
                                                                                }, 2500);
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        }, 500);
                                                    }
                                                });
                                            }
                                        }, 200);
                                    }
                                });
                            }
                        }, 500);

                    }
                });
            }
            else {
                Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                startActivity(intent);
            }

        }
        else {
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean checkDatabaseUser(String str)
    {
        return false;
    }
}
