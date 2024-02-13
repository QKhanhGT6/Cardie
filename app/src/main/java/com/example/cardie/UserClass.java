package com.example.cardie;

public class UserClass {
    private String UserID, Username, DeviceName;
    private int ExperiencePoints;

    public UserClass(){}

    public UserClass(String userID, String username, String deviceName, int ExperiencePoints) {
        UserID = userID;
        Username = username;
        DeviceName = deviceName;
        this.ExperiencePoints = ExperiencePoints;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public int getExperiencePoints() {
        return ExperiencePoints;
    }

    public void setExperiencePoints(int ExperiencePoints) {
        this.ExperiencePoints = ExperiencePoints;
    }
}
