package com.example.alanj.jingming_subbook_rev1;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by AlanJ on 2018-01-20.
 */

public class Subscription implements Parcelable {
    private String uuid;
    private String subscriptionName;
    private String dateStarted;
    private String monthlyCharges;
    private String comments;

    public Subscription() {}

    protected Subscription(Parcel in) {
        uuid = in.readString();
        subscriptionName = in.readString();
        dateStarted = in.readString();
        monthlyCharges = in.readString();
        comments = in.readString();
    }

    public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
        @Override
        public Subscription createFromParcel(Parcel in) {
            return new Subscription(in);
        }

        @Override
        public Subscription[] newArray(int size) {
            return new Subscription[size];
        }
    };

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
    public byte[] getUuidB() {
        return this.uuid.getBytes();
    }
    public char[] getUuidC() {
        return this.uuid.toCharArray();
    }
    public String getSubscriptionName() {
        return this.subscriptionName;
    }
    public byte[] getSubscriptionNameB() {
        return this.subscriptionName.getBytes();
    }
    public char[] getSubscriptionNameC() {
        return this.subscriptionName.toCharArray();
    }
    public int getSubscriptionNameLength() { return this.subscriptionName.length(); }
    public String getDateStarted() {
        return this.dateStarted;
    }
    public byte[] getDateStartedB() {
        return this.dateStarted.getBytes();
    }
    public char[] getDateStartedC() {
        return this.dateStarted.toCharArray();
    }
    public int getDateStartedLength() { return this.dateStarted.length(); }
    public Calendar getCalendar() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date theDate = simpleDateFormat.parse(this.dateStarted);
        Calendar myCal = new GregorianCalendar();
        myCal.setTime(theDate);
        return myCal;
    }
    public int getYear() throws ParseException {
        return this.getCalendar().get(Calendar.YEAR);
    }
    public int getMonth() throws ParseException {
        return this.getCalendar().get(Calendar.MONTH);
    }
    public int getDayOfMonth() throws ParseException {
        return this.getCalendar().get(Calendar.DAY_OF_MONTH);
    }
    public String getMonthlyCharges() {
        return this.monthlyCharges;
    }
    public byte[] getMonthlyChargesB() {
        return this.monthlyCharges.getBytes();
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
    public byte[] getCommentsB() {
        return this.comments.getBytes();
    }
    public char[] getCommentsC() {
        return this.comments.toCharArray();
    }
    public int getCommentsLength() {
        return this.comments.length();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(subscriptionName);
        parcel.writeString(dateStarted);
        parcel.writeString(monthlyCharges);
        parcel.writeString(comments);
    }
}
