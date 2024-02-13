package com.example.cardie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity {
    TextView tvUsername, tvUserID, tvLevel;
    String activeUsername;

    DatabaseFunctions connect;

    @Override
    public void onBackPressed() {
        boolean flag = false;
        if (flag) {
            super.onBackPressed();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_layout);

        connect = new DatabaseFunctions(UserProfileActivity.this);

        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("activeUsername");
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvUserID = (TextView) findViewById(R.id.tv_UserID);
        tvLevel = (TextView) findViewById(R.id.tv_level);
        tvUsername.setText(connect.getUsername(activeUsername));
        tvUserID.setText("id: " + activeUsername);

        tvLevel.setText("Level " + String.valueOf(connect.getUserLevel(activeUsername)));

        Button btnReturn = (Button) findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(UserProfileActivity.this, HomeActivity.class);
                intentBack.putExtra("activeUsername", activeUsername);
                startActivity(intentBack);
            }
        });
    }


}
