package com.example.cardie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardie.SetClass;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomeActivity extends Activity implements SetListener {
    List<SetClass> MySetList;
    List<SetClass> AllSetList;

    List<CardClass> AllCardList;

    String activeUsername;
    MediaPlayer mp;

    @Override
    public void onBackPressed() {
        boolean flag = false;
        if (flag) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        mp = MediaPlayer.create(HomeActivity.this, R.raw.ambient);

        //mp.start();
        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("activeUsername");



        DatabaseFunctions connect = new DatabaseFunctions(HomeActivity.this);
        MySetList = new ArrayList<>();
        MySetList = connect.getCardSetsForUser(activeUsername);
        AllSetList = new ArrayList<>();
        AllSetList = connect.getAllCardSets();
        AllCardList = new ArrayList<>();
        AllCardList = connect.getAllCards();
        //String bg = "https://placekitten.com/200/300";
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));
//        SetList.add(new SetClass("usd123","Animal",bg,"u01"));

        RecyclerView Set_RV_myset =  findViewById(R.id.recyclerview_myset);
        SetAdapter Set_Adapter_myset = new SetAdapter(HomeActivity.this, MySetList,HomeActivity.this);
        LinearLayoutManager linearLayoutManager_myset = new LinearLayoutManager(HomeActivity.this,RecyclerView.VERTICAL,false);
        Set_RV_myset.setLayoutManager(linearLayoutManager_myset);
        Set_RV_myset.setAdapter(Set_Adapter_myset);
//
//
        TextView totalSet = (TextView) findViewById(R.id.tv_total_sets);
        totalSet.setText(String.valueOf(AllSetList.size()));

        TextView totalCard = (TextView) findViewById(R.id.tv_total_cards);
        totalCard.setText(String.valueOf(AllCardList.size()));

        //Explore set
        RecyclerView Set_RV_peopleset =  findViewById(R.id.recyclerview_peopleset);
        SetAdapter Set_Adapter_peopleset = new SetAdapter(this, AllSetList,this);
        LinearLayoutManager linearLayoutManager_peopleset = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        Set_RV_peopleset.setLayoutManager(linearLayoutManager_peopleset);
        Set_RV_peopleset.setAdapter(Set_Adapter_peopleset);


        Button addSetButton = (Button) findViewById(R.id.btn_addSet);
        addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(HomeActivity.this, AddSetActivity.class);
                newIntent.putExtra("activeUsername", activeUsername);
                startActivity(newIntent);
            }
        });

        Button statistic = (Button) findViewById(R.id.btn_statistic);
        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(HomeActivity.this, StatisticActivity.class);
                newIntent.putExtra("activeUsername", activeUsername);
                startActivity(newIntent);
            }
        });

    }

    public void OnProfileIconButtonCLick(View view){
        try{
            Intent intentUserProfile = new Intent(HomeActivity.this, UserProfileActivity.class);
            intentUserProfile.putExtra("activeUsername", activeUsername);
            startActivity(intentUserProfile);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().trim(), Toast.LENGTH_SHORT).show();
        }
    }

    public void OnSettingButtonClick(View view) {
        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
        intent.putExtra("activeUsername", activeUsername);
        startActivity(intent);
    }
    @Override
    public void onSetClick(SetClass set) {
        Intent intent = new Intent(HomeActivity.this,CardInSetViewActivity.class);
        intent.putExtra("SetID", set.getSetID());
        intent.putExtra("SetName", set.getSetName());
        intent.putExtra("activeUsername", activeUsername);
        //intent.putExtra("Difficulty",set.getDifficulty());
        startActivity(intent);
    }

    @Override
    public void onPracticeButtonClick(String setID, String setName) {
        Intent intent = new Intent(this, SelectPracticeModeActivity.class);
        intent.putExtra("SetID", setID);
        intent.putExtra("activeUsername", activeUsername);
        intent.putExtra("SetName", setName);
        startActivity(intent);
    }

    @Override
    public void onSetClick(String setName) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
        mp.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }
}
