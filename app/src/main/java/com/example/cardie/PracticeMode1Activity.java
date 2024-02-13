package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PracticeMode1Activity extends Activity {
    String setID;
    String activeUsername;

    String setName;

    int count = 0;
    int score = 0;
    int pos = 0;

    Button choice1;
    Button choice2;
    Button choiceStop;
    List<CardClass> mData;

    PracticeMode1Adapter Adapter;
    ViewPager viewPager;

    TextView tv_setName;

    TextView currentCardNum;

    DatabaseFunctions connect;

    int coeff;

    int maxTime = 0;

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
        setContentView(R.layout.practice_mode_1_layout);




        Intent intent = getIntent();
        setID = intent.getStringExtra("SetID");
        activeUsername = intent.getStringExtra("activeUsername");
        setName = intent.getStringExtra("SetName");

        connect = new DatabaseFunctions(PracticeMode1Activity.this);
        coeff = connect.getAverageDifficulty(setID);

        choice1 = findViewById(R.id.btn_choice1);
        choice2 = findViewById(R.id.btn_choice2);
        choiceStop = findViewById(R.id.btn_choiceFinish);

        tv_setName = findViewById(R.id.tv_practice_mode_1_set_name);
        tv_setName.setText(setName);
        currentCardNum = findViewById(R.id.tv_currentQuestionNum);

        DatabaseFunctions connect = new DatabaseFunctions(PracticeMode1Activity.this);

        mData = connect.getCardsForCardSet(setID);

        maxTime = 10 * mData.size();

        currentCardNum.setText("Question " + String.valueOf(1)+"/"+(mData.size()));
        choice1.setText(mData.get(0).getCardPhrase());
        choice2.setText(mData.get(getRandomNum(0,mData.size()-1,0)).getCardPhrase());



        Adapter = new PracticeMode1Adapter(PracticeMode1Activity.this,mData);
        viewPager = findViewById(R.id.viewpager_practice_mode_1_card_definition);
        viewPager.setAdapter(Adapter);
        ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos=position;
                currentCardNum.setText("Question " + String.valueOf(position+1)+"/" +(mData.size()));
                choice1.setText(mData.get(position).getCardPhrase());
                choice2.setText(mData.get(getRandomNum(0,mData.size()-1,position)).getCardPhrase());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.addOnPageChangeListener(viewPagerListener); //Add Listener to ViewPager
        timer();
        initAns();


    }

    // Go to Result Screen when done:
    public void DoneTest(){
        Intent intent = new Intent(PracticeMode1Activity.this, PracticeMode1ResultActivity.class);

        double time_coeff = Double.valueOf(pos) / mData.size();
        double time_bonus_float = Math.max(maxTime-count,0) * time_coeff;
        int time_bonus = (int) time_bonus_float;
        int real_score = score*coeff + time_bonus;

        intent.putExtra("correct", String.valueOf(score));
        intent.putExtra("score", String.valueOf(real_score));
        intent.putExtra("time", String.valueOf(count));
        intent.putExtra("total", String.valueOf(mData.size()));

        intent.putExtra("SetID", setID);
        intent.putExtra("activeUsername", activeUsername);
        intent.putExtra("SetName", setName);

        startActivity(intent);
    }

    public int getRandomNum(int min,int max, int excludeNum){
        int rand=(int) (Math.random()*((max-min)+1)+min);
        while (excludeNum==rand){
            rand=(int) (Math.random() * ((max-min)+1)+min);
        }
        return rand;
    }
    public void initAns(){
        choiceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoneTest();
            }
        });
        //CHOICE1

        MediaPlayer mp_correct = MediaPlayer.create(PracticeMode1Activity.this, R.raw.correct_audio);
        MediaPlayer mp_wrong = MediaPlayer.create(PracticeMode1Activity.this, R.raw.wrong_audio);
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos<mData.size()-1){
                    if (choice1.getText().toString()==mData.get(pos).getCardPhrase()){
                        score++;
                        /*myDialog.setContentView(R.layout.popup_answer_right);
                        myDialog.show();*/
                        anim_correct();
                        mp_correct.start();;

                    }
                    else {
                        /*myDialog.setContentView(R.layout.popup_answer_wrong);
                        myDialog.show();*/
                        anim_wrong();
                        mp_wrong.start();
                    }
                    final int nextpos = pos + 1;

                    if (getRandomNum(0,2,1)==2){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(nextpos);
                                choice1.setText(mData.get(nextpos).getCardPhrase());
                                choice2.setText(mData.get(getRandomNum(0,mData.size()-1,nextpos)).getCardPhrase());
                            }
                        }, 1500);
                    }
                    else {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(nextpos);
                                choice2.setText(mData.get(nextpos).getCardPhrase());
                                choice1.setText(mData.get(getRandomNum(0,mData.size()-1,nextpos)).getCardPhrase());
                            }
                        }, 1500);
                    }
                }
                else {
                    if (choice1.getText().toString()==mData.get(pos).getCardPhrase()){
                        score++;
                       /* myDialog.setContentView(R.layout.popup_answer_right);
                        myDialog.show();*/
                        anim_correct();

                    }
                    else {
                 /*       myDialog.setContentView(R.layout.popup_answer_wrong);
                        myDialog.show();*/
                        anim_wrong();

                    }
                    DoneTest();
                }
            }

        });

        //CHOICE2

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos<mData.size()-1){
                    if (choice2.getText().toString()==mData.get(pos).getCardPhrase()){
                        score++;
/*                        myDialog.setContentView(R.layout.popup_answer_right);
                        myDialog.show();*/
                        anim_correct();
                        mp_correct.start();

                    }
                    else {
/*                        myDialog.setContentView(R.layout.popup_answer_wrong);
                        myDialog.show();*/
                        anim_wrong();
                        mp_wrong.start();

                    }
                    final int nextpos = pos + 1;

                    if (getRandomNum(0,2,1)==2){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(nextpos);
                                choice1.setText(mData.get(nextpos).getCardPhrase());
                                choice2.setText(mData.get(getRandomNum(0,mData.size()-1,nextpos)).getCardPhrase());
                            }
                        }, 1500);

                    }
                    else {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(nextpos);
                                choice2.setText(mData.get(nextpos).getCardPhrase());
                                choice1.setText(mData.get(getRandomNum(0,mData.size()-1,nextpos)).getCardPhrase());
                            }
                        }, 1500);

                    }
                }
                else {
                    if (choice2.getText().toString()==mData.get(pos).getCardPhrase()){
                        score++;
/*                        myDialog.setContentView(R.layout.popup_answer_right);
                        myDialog.show();*/
                        anim_correct();

                    }
                    else {
/*                        myDialog.setContentView(R.layout.popup_answer_wrong);
                        myDialog.show();*/
                        anim_wrong();

                    }
                    DoneTest();
                }
            }

        });

    }

    public void anim_correct() {
        final LottieAnimationView lottieAnimationCorrect=findViewById(R.id.correct_anim);
        lottieAnimationCorrect.setSpeed(2);
        lottieAnimationCorrect.playAnimation();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieAnimationCorrect.setSpeed(-2);
                lottieAnimationCorrect.playAnimation();
            }
        }, 1000);
    }
    public void anim_wrong() {
        final LottieAnimationView lottieAnimationWrong=findViewById(R.id.wrong_anim);
        lottieAnimationWrong.setSpeed(2);
        lottieAnimationWrong.playAnimation();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieAnimationWrong.setSpeed(-2);
                lottieAnimationWrong.playAnimation();
            }
        }, 1000);
    }

    public void timer(){
        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        count++;
                    }
                });
            }
        }, 1000, 1000);

    }
}
