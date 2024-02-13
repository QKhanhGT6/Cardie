package com.example.cardie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCardActivity extends Activity {

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
        setContentView(R.layout.add_card_layout);

        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("activeUsername");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.spinner_diff);
        spinner.setAdapter(adapter);

// Add items to the adapter
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");

        int spinnerPosition = adapter.getPosition("1");

        spinner.setSelection(spinnerPosition);

// Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();
    }

    CardClass newCard;

    // Go back to Set when click on User Icon:
    public void OnBackIconButton(View view){
        Intent intent = getIntent();
        String setID = intent.getStringExtra("SetID");
        String setName = intent.getStringExtra("SetName");
        Intent intentBack = new Intent(this, CardInSetViewActivity.class);
        intentBack.putExtra("SetID",setID);
        intentBack.putExtra("SetName", setName);
        intentBack.putExtra("activeUsername", activeUsername);
        startActivity(intentBack);
    }
//
    // Click on Add Card button to add new card:
    public void OnAddThisCardButtonClick(View view){
        Intent intent = getIntent();
        String setID = intent.getStringExtra("SetID");
        String setName = intent.getStringExtra("SetName");
        DatabaseFunctions connect = new DatabaseFunctions(AddCardActivity.this);
        String newId = connect.generateNewCardID();
        String newPhrase = ((EditText) findViewById(R.id.et_addcard_phrase)).getText().toString();
        String newType = ((EditText) findViewById(R.id.et_addcard_type)).getText().toString();
        String newDefinition = ((EditText) findViewById(R.id.et_addcard_definition)).getText().toString();
        String newBackground = ((EditText) findViewById(R.id.et_addcard_background)).getText().toString();
        int newDiff = Integer.parseInt(((Spinner)findViewById(R.id.spinner_diff) ).getSelectedItem().toString());

        if (!newId.matches("") && !newPhrase.matches("") && !newType.matches("") && !newDefinition.matches("") && !newBackground.matches("")){
            try {
                newCard = new CardClass(newId, newBackground, newPhrase, newType, newDefinition, "", newDiff, setID);
                newCard.insertCardToDb(AddCardActivity.this);
            }
            catch (Exception e){
                Intent intentBack = new Intent(this, CardInSetViewActivity.class);
                intentBack.putExtra("SetID",setID);
                intentBack.putExtra("SetName", setName);
                intentBack.putExtra("activeUsername", activeUsername);
                startActivity(intentBack);
            }
            Intent intentBack = new Intent(this, CardInSetViewActivity.class);
            intentBack.putExtra("SetID",setID);
            intentBack.putExtra("SetName", setName);
            intentBack.putExtra("activeUsername", activeUsername);
            startActivity(intentBack);
        }
        else {
            String message = "Please enter all fields!";
            Toast.makeText(AddCardActivity.this,message, Toast.LENGTH_SHORT).show();
        }

    }
}
