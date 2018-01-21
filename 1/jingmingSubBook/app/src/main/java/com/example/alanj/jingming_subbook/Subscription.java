package com.example.alanj.jingming_subbook;

import android.os.AsyncTask;

import java.io.FileOutputStream;

/**
 * Created by AlanJ on 2018-01-19.
 */

public class Subscription {
    protected SubBook subBook;
    public Subscription() {
        this.subBook = new SubBook();
    }
    public Subscription(SubBook subBooks) {
        this.subBook = subBooks;
    }
    public void setSubBook(SubBook subBook) {
        this.subBook = subBook;
    }
    public SubBook getSubBook() {
        return this.subBook;
    }
}
