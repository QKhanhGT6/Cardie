package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ResultActivity extends Activity {
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
        setContentView(R.layout.practice_mode_1_result_layout);
    }

    // Go back to home(main) screen when click on Finish button:
    public void OnHomeIconButton(View view){
        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }
}
