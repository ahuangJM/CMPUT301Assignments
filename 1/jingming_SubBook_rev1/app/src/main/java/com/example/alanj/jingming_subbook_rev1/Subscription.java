package com.example.alanj.jingming_subbook_rev1;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by AlanJ on 2018-01-20.
 */

public class Subscription {
    private String uuid;
    private String subscriptionName;
    private String dateStarted;
    private String monthlyCharges;
    private String comments;

    public Subscription() {}

    public void setUuid(){
        this.uuid = UUID.randomUUID().toString();
    }
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    public void setSubscriptionName(String subscriptionName) throws InputTooLongException {
        if (subscriptionName.length() > 20) {
            Log.e("Error: ", "Subscription Name Too Long");
            throw new InputTooLongException();
        }
        this.subscriptionName = subscriptionName;
    }
    public void setDateStarted(String dateStarted) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedCurrentDate = null;
        try {
            convertedCurrentDate = simpleDateFormat.parse(dateStarted);
        } catch (ParseException e) {
            Log.e("Error: ", "Date Parse Failed");
            e.printStackTrace();
        }
        this.dateStarted = simpleDateFormat.format(convertedCurrentDate);
    }
    public void setMonthlyCharges(String monthlyCharges) throws ExpectedNonNegativeException {
        if (Double.parseDouble(monthlyCharges) < 0) {
            Log.e("Error: ", "monthlyCharges Is Negative");
            throw new ExpectedNonNegativeException();
        }
        this.monthlyCharges = monthlyCharges;
    }
    public void setComments(String comments) throws InputTooLongException {
        if (comments.length() > 30) {
            Log.e("Error: ", "Subscription Name Too Long");
            throw new InputTooLongException();
        }
        this.comments = comments;
    }
    public String getUuid() {
        return this.uuid;
    }
    public char[] getUuidC() {
        return this.uuid.toCharArray();
    }
    public String getSubscriptionName() {
        return this.subscriptionName;
    }
    public char[] getSubscriptionNameC() {
        return this.subscriptionName.toCharArray();
    }
    public int getSubscriptionNameLength() { return this.subscriptionName.length(); }
    public String getDateStarted() {
        return this.dateStarted;
    }
    public char[] getDateStartedC() {
        return this.dateStarted.toCharArray();
    }
    public int getDateStartedLength() { return this.dateStarted.length(); }
    public String getMonthlyCharges() {
        return this.monthlyCharges;
    }
    public char[] getMonthlyChargesC() {
        return this.monthlyCharges.toCharArray();
    }
    public int getMonthlyChargesLength() {
        return this.monthlyCharges.length();
    }
    public String getComments() {
        return this.comments;
    }
    public char[] getCommentsC() {
        return this.comments.toCharArray();
    }
    public int getCommentsLength() {
        return this.comments.length();
    }

}
