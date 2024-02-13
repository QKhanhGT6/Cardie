package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddSetActivity extends Activity {
    SetClass newSet;
    String activeUsername;

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
        setContentView(R.layout.add_set_layout);
        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("activeUsername");
        TextView backButton = (TextView) findViewById(R.id.tv_return);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(AddSetActivity.this, HomeActivity.class);
                intentBack.putExtra("activeUsername", activeUsername);
                startActivity(intentBack);
            }
        });
    }


    // Click on Add Set button to add new set:
    public void OnAddThisSetButtonClick(View view){

        DatabaseFunctions connect = new DatabaseFunctions(AddSetActivity.this);
        String newId = connect.generateNewCardSetID();
        String newName = ((EditText) findViewById(R.id.et_add_set_name)).getText().toString();
        String newBackground = ((EditText) findViewById(R.id.et_add_set_background)).getText().toString();
        if (!newId.matches("") && !newName.matches("") && !newBackground.matches("")){
            try {
                newSet = new SetClass(newId, newName,newBackground,activeUsername);
                newSet.insertSetToDb(AddSetActivity.this);
            }
            catch (Exception e){
                Intent intentBack = new Intent(this, HomeActivity.class);
                intentBack.putExtra("activeUsername", activeUsername);
                startActivity(intentBack);
            }
            Intent intentBack = new Intent(this, HomeActivity.class);
            intentBack.putExtra("activeUsername", activeUsername);
            startActivity(intentBack);
        }
        else {
            String message = "Please enter all fields!";
            Toast.makeText(AddSetActivity.this,message, Toast.LENGTH_SHORT).show();
        }

    }
}
