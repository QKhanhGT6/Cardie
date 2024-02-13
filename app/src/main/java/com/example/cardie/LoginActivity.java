package com.example.cardie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.stream.Collectors;

public class LoginActivity extends Activity {
    EditText etUsername;

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
        setContentView(R.layout.login_layout);
    }

    // Click on OK button to check db:
    // Check if Username already in db, else add to db:
    public void OnLogInButtonClick(View view){
        etUsername = (EditText) findViewById(R.id.et_username);
        String username = etUsername.getText().toString().trim();
//        if (etUsername.getText() not in db)
//        Add to db, add information to UserProfile
        DatabaseFunctions connect = new DatabaseFunctions(this);
        try {
            List<UserClass> userClassList = connect.getAllUsers();
            if (userClassList.stream().anyMatch(item -> item.getUsername().equals(username)) == false){
                String id = String.valueOf(userClassList.size());
                String deviceName = android.os.Build.MODEL;
                String userID = connect.generateNewUserID();
                connect.insertUser(userID, username, deviceName, 0);
            }
            Toast.makeText(this, "Log in successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("activeUsername", connect.getUserID(username));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().trim(), Toast.LENGTH_SHORT).show();
        }
    }
}
