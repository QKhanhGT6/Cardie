package com.example.cardie;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PracticeClass {
    ConnectDatabase connect;
    private String PracticeID, UserID, SetID;
    private int CorrectAnswer, TotalQuestions, ExperienceEarned, Mode;
    private Date Timestamps;

    public PracticeClass(String practiceID, String userID, String setID, int mode, int correctAnswer, int totalQuestions, int experienceEarned, Date timestamps) {
        PracticeID = practiceID;
        UserID = userID;
        SetID = setID;
        Mode = mode;
        CorrectAnswer = correctAnswer;
        TotalQuestions = totalQuestions;
        ExperienceEarned = experienceEarned;
        Timestamps = timestamps;
    }

    public String getPracticeID() {
        return PracticeID;
    }

    public void setPracticeID(String practiceID) {
        PracticeID = practiceID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getSetID() {
        return SetID;
    }

    public void setSetID(String setID) {
        SetID = setID;
    }

    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public int getWrongAnswer(){
        return TotalQuestions - CorrectAnswer;
    }

    public int getTotalQuestions() {
        return TotalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        TotalQuestions = totalQuestions;
    }

    public int getExperienceEarned() {
        return ExperienceEarned;
    }

    public void setExperienceEarned(int experienceEarned) {
        ExperienceEarned = experienceEarned;
    }

    public Date getTimestamps() {
        return Timestamps;
    }

    public void setTimestamps(Date timestamps) {
        Timestamps = timestamps;
    }

    public void insertPracticeToDb(Context context){
        DatabaseFunctions connect = new DatabaseFunctions(context);
        connect.insertPractice(this.PracticeID, this.UserID, this.SetID, this.Mode, this.CorrectAnswer, this.TotalQuestions, this.ExperienceEarned);
    }

    // // Sử dụng get để lấy data nếu hàm trên bị lỗi
//    public void ínertPracticeToDb(PracticeClass practice){
//        String query = "INSERT INTO Practice (PracticeID, UserID, SetID, Mode, CorrectAnswers, TotalQuestions, ExperienceEarned, Timestamp) " +
//                "VALUES ('" + practice.getPracticeID() + "', '" + practice.getUserID() + "', '" + practice.getSetID() + "', '" + practice.getMode() + "', '" +
//                practice.getCorrectAnswer() + "', '" + practice.getTotalQuestions() + "', '" + practice.getExperienceEarned() + "', '" + practice.getTimestamps() + "')";
//        connect.WriteTable(query);
//    }
    public List<PracticeClass> getPracticeFromDb(String userId) {
        List<PracticeClass> practiceList = new ArrayList<>();
        String query = "SELECT * FROM Practice WHERE UserID = '" + userId + "'";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int practiceIdIndex = cursor.getColumnIndex("PracticeID");
                int setIdIndex = cursor.getColumnIndex("SetID");
                int modeIndex = cursor.getColumnIndex("Mode");
                int correctAnswersIndex = cursor.getColumnIndex("CorrectAnswers");
                int totalQuestionsIndex = cursor.getColumnIndex("TotalQuestions");
                int experienceEarnedIndex = cursor.getColumnIndex("ExperienceEarned");
                int timestampIndex = cursor.getColumnIndex("Timestamp");

                do {
                    if (practiceIdIndex >= 0) {
                        String practiceId = cursor.getString(practiceIdIndex);
                        String setId = cursor.getString(setIdIndex);
                        int mode = cursor.getInt(modeIndex);
                        int correctAnswers = cursor.getInt(correctAnswersIndex);
                        int totalQuestions = cursor.getInt(totalQuestionsIndex);
                        int experienceEarned = cursor.getInt(experienceEarnedIndex);

                        // Convert the timestamp string to a Date object
                        String timestampStr = cursor.getString(timestampIndex);
                        Date timestamp = parseDateFromString(timestampStr);

                        PracticeClass practice = new PracticeClass(practiceId, userId, setId, mode, correctAnswers, totalQuestions, experienceEarned, timestamp);
                        practiceList.add(practice);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return practiceList;
    }

    private Date parseDateFromString(String timestampStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            return dateFormat.parse(timestampStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
