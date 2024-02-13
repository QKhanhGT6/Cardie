package com.example.cardie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
    }

    public void SwitchToCard (View view)
    {
//        Intent it = new Intent(this, EditCardSet.class);
//        startActivity(it);
    }

    public void ConnectDB(View view) {
        // Delete the existing database if it exists
        //this.deleteDatabase("Cardie.sqlite");

        ConnectDatabase connect = new ConnectDatabase(this, "Cardie.sqlite", null, 1);

        String commandUsers = "CREATE TABLE IF NOT EXISTS Users (" +
                "UserID TEXT PRIMARY KEY, " +
                "Username TEXT, " +
                "Password TEXT, " +
                "Level INTEGER, " +
                "ExperiencePoints INTEGER)";
        connect.WriteTable(commandUsers);

        String commandCardSet = "CREATE TABLE IF NOT EXISTS CardSet (" +
                "SetID TEXT PRIMARY KEY, " +
                "SetName TEXT, " +
                "SetImage TEXT, " +
                "UserID TEXT, " +
                "FOREIGN KEY(UserID) REFERENCES Users(UserID))";
        connect.WriteTable(commandCardSet);

        String commandCard = "CREATE TABLE IF NOT EXISTS Card (" +
                "CardID TEXT PRIMARY KEY, " +
                "CardImage TEXT, " +
                "CardText TEXT, " +
                "CardSound TEXT, " +
                "DifficultyLevel INTEGER, " +
                "SetID TEXT, " +
                "FOREIGN KEY(SetID) REFERENCES CardSet(SetID))";
        connect.WriteTable(commandCard);

        String commandPractice = "CREATE TABLE IF NOT EXISTS Practice (" +
                "PracticeID TEXT PRIMARY KEY, " +
                "UserID TEXT, " +
                "SetID TEXT, " +
                "Mode INTEGER, " +
                "CorrectAnswers INTEGER, " +
                "WrongAnswers INTEGER, " +
                "TotalQuestions INTEGER, " +
                "ExperienceEarned INTEGER, " +
                "Timestamp DATETIME, " +
                "FOREIGN KEY(UserID) REFERENCES Users(UserID), " +
                "FOREIGN KEY(SetID) REFERENCES CardSet(SetID))";
        connect.WriteTable(commandPractice);

        Toast.makeText(this, "Successfully created database!", Toast.LENGTH_SHORT).show();
    }

    public void CreateUsername(View view)
    {
//        ConnectDatabase connect = new ConnectDatabase(this, "Cardie.sqlite", null, 1);
//        // Check if the provided username already exists
//        String newUsername = ((EditText) findViewById(R.id.edtTxtUsername)).getText().toString().trim();
//        String checkUsernameQuery = "SELECT COUNT(*) FROM Users WHERE Username = '" + newUsername + "'";
//        Cursor cursor = connect.ReadTable(checkUsernameQuery);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            int userCount = cursor.getInt(0);
//            cursor.close();
//
//            if (userCount > 0)
//            {
//                // Username already exists, show a toast
//                Toast.makeText(this, "Username exists", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                // Username is unique, insert the new user
//                String lastUserIdQuery = "SELECT MAX(UserID) FROM Users";
//                Cursor idCursor = connect.ReadTable(lastUserIdQuery);
//                String lastUserId = "U001"; // Default if no users exist yet
//
//                if (idCursor != null) {
//                    idCursor.moveToFirst();
//                    if (!idCursor.isNull(0)) {
//                        // Increment the last UserID to create a new one
//                        int lastUserNumber = Integer.parseInt(idCursor.getString(0).substring(1));
//                        lastUserNumber++;
//                        lastUserId = "U" + String.format("%03d", lastUserNumber);
//                    }
//                    idCursor.close();
//                }
//
//                // Insert the new user into the Users table
//                String insertUserQuery = "INSERT INTO Users (UserID, Username, Password, Level, ExperiencePoints) " +
//                        "VALUES ('" + lastUserId + "', '" + newUsername + "', '123', 0, 0)";
//                connect.WriteTable(insertUserQuery);
//
//                Toast.makeText(this, "User account created successfully.", Toast.LENGTH_SHORT).show();
//
//                // Check if the default card set exists for the new user
//                String checkDefaultSetQuery = "SELECT COUNT(*) FROM CardSet WHERE SetName = 'Default Set' AND UserID = '" + lastUserId + "'";
//                Cursor defaultSetCursor = connect.ReadTable(checkDefaultSetQuery);
//
//                if (defaultSetCursor != null) {
//                    defaultSetCursor.moveToFirst();
//                    int defaultSetCount = defaultSetCursor.getInt(0);
//                    defaultSetCursor.close();
//
//                    if (defaultSetCount == 0) {
//                        // Default card set doesn't exist, insert it
//                        String insertDefaultSetQuery = "INSERT INTO CardSet (SetID, SetName, SetImage, UserID) " +
//                                "VALUES ('DF', 'Default Set', 'No Image', '" + lastUserId + "')";
//                        connect.WriteTable(insertDefaultSetQuery);
//                    }
//                } else {
//                    Toast.makeText(this, "An error occurred while checking the default card set.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//        else
//        {
//            Toast.makeText(this, "An error occurred while checking the username.", Toast.LENGTH_SHORT).show();
//        }
    }
}