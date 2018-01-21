package com.example.alanj.jingming_subbook_rev1;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by AlanJ on 2018-01-20.
 */

public class Book implements Parcelable {
    private ArrayList<Subscription> book;
    public Book() {}

    protected Book(Parcel in) {
        book = in.createTypedArrayList(Subscription.CREATOR);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public void setBook() {
        this.book = new ArrayList<>();
    }
    public void setBook(ArrayList<Subscription> book) {
        this.book = book;
    }
    public ArrayList<Subscription> getBook() {
        return this.book;
    }
    public void addSubscription(Subscription newSubscription) throws NullOrEmptyException {
        if (newSubscription.getUuid() == null ||
                newSubscription.getUuid().isEmpty()) {
            Log.e("Error: ","New Subscription UUID Is Null or Empty");
            throw new NullOrEmptyException();
        }
        if (newSubscription.getSubscriptionName() == null ||
                newSubscription.getSubscriptionName().isEmpty()) {
            Log.e("Error: ","New Subscription Subscription NameIs Null or Empty");
            throw new NullOrEmptyException();
        }
        if (newSubscription.getDateStarted() == null ||
                newSubscription.getDateStarted().isEmpty()) {
            Log.e("Error: ","New Subscription Date Started Is Null or Empty");
            throw new NullOrEmptyException();
        }
        if (newSubscription.getMonthlyCharges() == null ||
                newSubscription.getMonthlyCharges().isEmpty()) {
            Log.e("Error: ","New Subscription Monthly Charges Is Null or Empty");
            throw new NullOrEmptyException();
        }
        this.book.add(newSubscription);
    }
    public void removeSubscription(Subscription subscription) {
        this.book.remove(subscription);
    }
    public Boolean exists(ArrayList<Subscription> book, Subscription subscription) {
        for (Subscription subscription1 : book) {
            if (subscription.getUuid() == subscription1.getUuid()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.book);
    }
}
