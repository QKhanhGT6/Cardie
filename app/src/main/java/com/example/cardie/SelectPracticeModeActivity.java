package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectPracticeModeActivity extends Activity {

    String setID;
    String activeUsername;

    String setName;

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
        setContentView(R.layout.select_practice_mode_layout);
        Intent intent = getIntent();
        setID = intent.getStringExtra("SetID");
        activeUsername = intent.getStringExtra("activeUsername");
        setName = intent.getStringExtra("SetName");
    }

    // Go back to Set when click on User Icon:
    public void OnBackIconButton(View view){
        Intent intentBack = new Intent(this, HomeActivity.class);
        intentBack.putExtra("SetID", setID);
        intentBack.putExtra("activeUsername", activeUsername);
        intentBack.putExtra("SetName", setName);
        startActivity(intentBack);
    }

    // Click on test mode item:
    public void OnTestModeItemClick(View view){
        Intent intentTest = new Intent(this, PracticeMode1Activity.class);
        intentTest.putExtra("SetID", setID);
        intentTest.putExtra("activeUsername", activeUsername);
        intentTest.putExtra("SetName", setName);
        startActivity(intentTest);
    }
}
