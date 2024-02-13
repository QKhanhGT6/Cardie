package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class CardInSetViewActivity extends Activity {

    CardAdapter Adapter;
    ViewPager viewPager;
    List<CardClass> mData;
    TextView setName;
    TextView cardNum;
    ImageView diffEasy;
    ImageView diffMedium;
    ImageView diffHard;

    Button addCard;

    TextView tv_return;

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
        setContentView(R.layout.card_in_set_view_layout);

        setName = findViewById(R.id.SetName);
        cardNum = findViewById(R.id.tv_total_cards);

        diffEasy = findViewById(R.id.diffEasy);
        diffMedium = findViewById(R.id.diffMedium);
        diffHard = findViewById(R.id.diffHard);
        //addcards=findViewById(R.id.add_card);
//        addcards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addcard();
//            }
//        });

        Intent intent = getIntent();
        String setID = intent.getStringExtra("SetID");
        String setName = intent.getStringExtra("SetName");
        String activeUsername = intent.getStringExtra("activeUsername");

        DatabaseFunctions connect = new DatabaseFunctions(CardInSetViewActivity.this);

        mData = new ArrayList<>();
        mData = connect.getCardsForCardSet(setID);
        cardNum.setText(String.valueOf(mData.size()) + " cards");
//        String imageurl = "https://placekitten.com/200/300";
//        mData.add(new CardClass("C01", imageurl,"Cat","Animal", "A cute house animal", "", 0,""));
//        mData.add(new CardClass("C02", imageurl,"Cat","Animal", "A cute house animal", "", 0,""));
//        mData.add(new CardClass("C03", imageurl,"Cat","Animal", "A cute house animal", "", 0,""));
        //get information to display
        initialize();
        Adapter = new CardAdapter(mData,CardInSetViewActivity.this);
        viewPager = findViewById(R.id.CardViewPager);
        viewPager.setAdapter(Adapter);
//        // Declare Event's Listener
//        ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        };
//        viewPager.addOnPageChangeListener(viewPagerListener); //Add Listener to ViewPager
//
//        //final TextView cardDef=findViewById(R.id.tv_CardDefinition);

        addCard = findViewById(R.id.btn_add_card);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCardIntent = new Intent(CardInSetViewActivity.this, AddCardActivity.class);
                addCardIntent.putExtra("SetID",setID);
                addCardIntent.putExtra("SetName", setName);
                addCardIntent.putExtra("activeUsername", activeUsername);
                startActivity(addCardIntent);
            }
        });


       tv_return = (TextView) findViewById(R.id.tv_return);
       tv_return.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent homeIntent = new Intent(CardInSetViewActivity.this, HomeActivity.class);
               homeIntent.putExtra("activeUsername", activeUsername);
               startActivity(homeIntent);
           }
       });

    }

    private void initialize()
    {
        Intent previousIntent = this.getIntent();
        setName.setText(previousIntent.getExtras().getString("SetName"));

        int diff = previousIntent.getExtras().getInt("SetDifficulty");
        switch (diff)
        {
            case 1:
                diffEasy.setBackgroundTintList(getResources().getColorStateList(R.color.greendiff,null));
                break;
            case 2:
                diffEasy.setBackgroundTintList(getResources().getColorStateList(R.color.greendiff,null));
                diffEasy.setBackgroundTintList(getResources().getColorStateList(R.color.backgroundbluelight,null));
                break;
            case 3:
                diffEasy.setBackgroundTintList(getResources().getColorStateList(R.color.greendiff,null));
                diffEasy.setBackgroundTintList(getResources().getColorStateList(R.color.backgroundbluelight,null));
                diffHard.setBackgroundTintList(getResources().getColorStateList(R.color.backgroundOrange,null));
                break;
            default:
                break;
        }
    }
//    public void OnAddCardButtonClick(){
//        Intent intent = getIntent();
//        String setID = intent.getStringExtra("SetID");
//        Intent addCardIntent = new Intent(CardInSetViewActivity.this, AddCardActivity.class);
//        addCardIntent.putExtra("SetID",setID);
//        startActivity(addCardIntent);
//    }


}
