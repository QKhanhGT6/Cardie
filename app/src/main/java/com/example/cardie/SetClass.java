package com.example.cardie;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class SetClass {
    private String SetID, SetName, SetImage, UserID;
    private List<CardClass> cardClassList;

    public SetClass(String setID, String setName, String setImage, String userID) {
        SetID = setID;
        SetName = setName;
        SetImage = setImage;
        UserID = userID;
    }

    public String getSetID() {
        return SetID;
    }

    public void setSetID(String setID) {
        SetID = setID;
    }

    public String getSetName() {
        return SetName;
    }

    public void setSetName(String setName) {
        SetName = setName;
    }

    public String getSetImage() {
        return SetImage;
    }

    public void setSetImage(String SetImage) {
        SetImage = SetImage;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }


    public void insertSetToDb(Context context){
        DatabaseFunctions connect = new DatabaseFunctions(context);
        connect.insertCardSet(this.SetID, this.SetName, this.SetImage, this.UserID);
    }

    public int getDifficulty(Context context){
        DatabaseFunctions connect = new DatabaseFunctions(context);
        int diff = connect.getAverageDifficulty(this.SetID);
        return diff;
    }

}
