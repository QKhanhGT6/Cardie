package com.example.cardie;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class CardClass {
    private String CardID, CardImage, CardPhrase, CardType, CardDefinition, CardSound, SetID;
    private int DifficultyLevel;

    public CardClass(String cardID, String cardImage, String cardPhrase, String cardType, String cardDefinition, String cardSound, int difficultyLevel, String setID) {
        CardID = cardID;
        CardImage = cardImage;
        CardPhrase = cardPhrase;
        CardType = cardType;
        CardDefinition = cardDefinition;
        CardSound = cardSound;
        DifficultyLevel = difficultyLevel;
        SetID = setID;
    }

    public String getCardID() {
        return CardID;
    }

    public void setCardID(String cardID) {
        CardID = cardID;
    }

    public String getCardImage() {
        return CardImage;
    }

    public void setCardImage(String cardImage) {
        CardImage = cardImage;
    }

    public String getCardPhrase() {
        return CardPhrase;
    }

    public void setCardPhrase(String cardPhrase) {
        CardPhrase = cardPhrase;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardDefinition() {
        return CardDefinition;
    }

    public void setCardDefinition(String cardDefinition) {
        CardDefinition = cardDefinition;
    }

    public String getCardSound() {
        return CardSound;
    }

    public void setCardSound(String cardSound) {
        CardSound = cardSound;
    }

    public String getSetID() {
        return SetID;
    }

    public void setSetID(String setID) {
        SetID = setID;
    }

    public int getDifficultyLevel() {
        return DifficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        DifficultyLevel = difficultyLevel;
    }

    public void insertCardToDb(Context context){
        DatabaseFunctions connect = new DatabaseFunctions(context);
        connect.insertCard(this.CardID, this.CardImage, this.CardPhrase, this.CardType, this.CardDefinition, this.CardSound, this.DifficultyLevel, this.SetID);
    }

}
