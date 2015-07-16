package com.andrea.lostdogposter;

import android.util.Log;

public class LostDogModel {
    public static final String TAG = "LostDogModel";
    private String dogsName;
    private String description;
    private String microchipNum;
    private String ownerName;
    private String ownerPhone;

    public String getDogsName() {
        Log.d(TAG, "*** In getDogsName dogsName = " + dogsName + "***");
        return dogsName;
    }
    public String getDescription() {
        return description;
    }
    public String getMicrochipNum() {
        return microchipNum;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setDogsName(String dogsName) {
        this.dogsName = dogsName;
        Log.d(TAG, "*** In setDogsName dogsName = " + dogsName + "***");
    }

    public void setDescription(String description) {
        this.description = description;
        Log.d(TAG, "*** In setDescription description = " + description + "***");
    }

    public void setMicrochipNum(String microchipNum) {
        this.microchipNum = microchipNum;
        Log.d(TAG, "*** In setMicrochipNum microchipNum = " + microchipNum + "***");
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

}