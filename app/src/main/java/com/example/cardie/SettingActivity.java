package com.example.cardie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {
    AudioManager audioManager;

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
        setContentView(R.layout.setting_layout);

        String activeUsername;

        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("activeUsername");

        TextView btnReturn = (TextView) findViewById(R.id.return_setting);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(SettingActivity.this, HomeActivity.class);
                intentBack.putExtra("activeUsername", activeUsername);
                startActivity(intentBack);
            }
        });

        try {
            audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Click on Back button to get back to HomeActivity
//    public void OnBackButtonClcik(View view){
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//    }

    // Volume up by clicking the Volume up button:
    public void OnVolumeUpButtonClick(View view){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
//        Toast.makeText(this, "Volume up already", Toast.LENGTH_SHORT).show();
    }

    // Volume down by clicking the Volume up button:
    public void OnVolumeDownButtonClick(View view){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
//        Toast.makeText(this, "Volume down already", Toast.LENGTH_SHORT).show();
    }

    // Click on Logout button to get back to LoginActivity:
    public void OnLogOutButtonClick(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
