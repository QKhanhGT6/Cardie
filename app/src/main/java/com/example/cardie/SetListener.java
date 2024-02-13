package com.example.cardie;

import com.example.cardie.SetClass;

public interface SetListener {
    void onSetClick(SetClass set);
    void onPracticeButtonClick(String setID, String setName);
    void onSetClick(String setName);
}
