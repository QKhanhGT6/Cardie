package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class StatisticActivity extends Activity {
    @Override
    public void onBackPressed() {
        boolean flag = false;
        if (flag) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_layout);

        Intent intent = getIntent();
        String activeUsername = intent.getStringExtra("activeUsername");

        DatabaseFunctions connect = new DatabaseFunctions(StatisticActivity.this);
        TextView tvReturn = findViewById(R.id.tv_return);

        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(StatisticActivity.this, HomeActivity.class);
                intentBack.putExtra("activeUsername", activeUsername);
                startActivity(intentBack);
            }
        });


        TextView tvPracticeDone = findViewById(R.id.tv_statistic_practice_done);
        tvPracticeDone.setText(String.valueOf(connect.getPracticeCountForUser(activeUsername)));

        TextView tvExp = findViewById(R.id.tv_statistic_total_exp);
        tvExp.setText(String.valueOf(connect.getUserExp(activeUsername)));

        TextView tvAcc = findViewById(R.id.tv_statistic_accuracy);
        double roundOff = Math.round(connect.getAccuracyUser(activeUsername) * 100.0) / 100.0;
        tvAcc.setText(String.valueOf(roundOff));
    }
}
