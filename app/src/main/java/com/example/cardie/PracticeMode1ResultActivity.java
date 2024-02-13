package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Date;

public class PracticeMode1ResultActivity extends Activity {

    String setID;
    String setName;
    String activeUsername;

    int correct;
    int score;
    int time;
    int total;

    int row;

    TextView correctCount;
    TextView timeCount;

    TextView rowCount;

    TextView scoreCount;

    Button btnContinue;
    Button btnFinish;

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
        setContentView(R.layout.practice_mode_1_result_layout);

        Intent intent = getIntent();
        setID = intent.getStringExtra("SetID");
        setName = intent.getStringExtra("SetName");
        activeUsername = intent.getStringExtra("activeUsername");

        correct = Integer.parseInt(intent.getStringExtra("correct"));
        score = Integer.parseInt(intent.getStringExtra("score"));
        time = Integer.parseInt(intent.getStringExtra("time"));
        total = Integer.parseInt(intent.getStringExtra("total"));

        correctCount = findViewById(R.id.tv_practice_mode_1_correct_count);
        correctCount.setText(String.valueOf(correct) + "/" + String.valueOf(total));

        timeCount = findViewById(R.id.tv_practice_mode_1_time_count);
        timeCount.setText(String.valueOf(time)+"s");

        scoreCount = findViewById(R.id.tv_practice_mode_1_score_count);
        scoreCount.setText(String.valueOf(score));

        btnContinue = findViewById(R.id.btn_practice_mode_1_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTest = new Intent(PracticeMode1ResultActivity.this, PracticeMode1Activity.class);
                intentTest.putExtra("SetID", setID);
                intentTest.putExtra("activeUsername", activeUsername);
                intentTest.putExtra("SetName", setName);
                startActivity(intentTest);
            }
        });

        btnFinish = findViewById(R.id.btn_practice_mode_1_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(PracticeMode1ResultActivity.this, HomeActivity.class);
                intentBack.putExtra("SetID", setID);
                intentBack.putExtra("activeUsername", activeUsername);
                intentBack.putExtra("SetName", setName);
                startActivity(intentBack);
            }
        });

        DatabaseFunctions connect = new DatabaseFunctions(PracticeMode1ResultActivity.this);

        String newID = connect.generateNewPracticeID();
        Date date = new Date();
        PracticeClass newPractice = new PracticeClass(newID, activeUsername, setID, 1, correct, total, score,date);
        newPractice.insertPracticeToDb(PracticeMode1ResultActivity.this);

        connect.updateUserLevel(activeUsername, score);

        rowCount = findViewById(R.id.tv_practice_mode_1_row);
        row = connect.getPracticeCountForUser(activeUsername);
        rowCount.setText(String.valueOf(row));



    }
}
