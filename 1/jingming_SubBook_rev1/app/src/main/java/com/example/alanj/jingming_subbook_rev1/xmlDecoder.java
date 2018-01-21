package com.example.alanj.jingming_subbook_rev1;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by AlanJ on 2018-01-20.
 */

public class xmlDecoder implements IxmlFIle {
    private FileInputStream fileInputStream;
    private Context currentContext;
    private String DEFAULT_FILE_NAME = "book.xml";
    public xmlDecoder(Context currentContext) {
        this.currentContext = currentContext;
    }
    public xmlDecoder(String fileName, Context currentContext) {
        this.currentContext = currentContext;
        try {
            this.fileInputStream = this.currentContext.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            Log.e("Error: ", "File Not Found");
            e.printStackTrace();
        }
    }
    public void setFileInputStream() {
        try {
            this.fileInputStream = this.getCurrentContext().openFileInput(this.DEFAULT_FILE_NAME);
        } catch (FileNotFoundException e) {
            Log.e("Error: ", "File Not Found");
            e.printStackTrace();
        }
    }
    public void setFileInputStream(FileInputStream fileInputStream) {
        if (fileInputStream == null) {
            Log.e("Error: ", "File Input Stream Is Null");
            throw new NullPointerException();
        }
        this.fileInputStream = fileInputStream;
    }
    public void setFileInputStream(String fileName) {
        try {
            this.fileInputStream = this.currentContext.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            Log.e("Error: ", "File Not Found");
            e.printStackTrace();
        }
    }
    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }
    public FileInputStream getFileInputStream() {
        return this.fileInputStream;
    }
    public Context getCurrentContext() {
        return this.currentContext;
    }
    public String getDefaultFileName() {
        return this.DEFAULT_FILE_NAME;
    }
    public Subscription decodeRow(String row, String uuid) throws InputTooLongException, ExpectedNonNegativeException {
        if (uuid == null || uuid.isEmpty()) {
            Log.e("Error", "UUID is Null or Empty");
            throw new NullPointerException();
        }
        if (row == null || row.isEmpty()) {
            Log.e("Error", "Row is Null or Empty");
            throw new NullPointerException();
        }
        Subscription subscription = new Subscription();
        String[] dataParts = row.split("\\|");
        subscription.setUuid(uuid);
        subscription.setSubscriptionName(dataParts[0]);
        subscription.setDateStarted(dataParts[1]);
        subscription.setMonthlyCharges(dataParts[2]);
        subscription.setComments(dataParts[3]);
        return subscription;
    }
    public Book decodeBook() throws InputTooLongException, ExpectedNonNegativeException, NullOrEmptyException {
        Book book = new Book();
        book.setBook();
        InputStreamReader inputStreamReader = new InputStreamReader(this.fileInputStream);
        char[] inputBuffer = new char[0];
        try {
            inputBuffer = new char[fileInputStream.available()];
        } catch (IOException e) {
            Log.e("Error", "File Input Stream Is Unavailable");
            e.printStackTrace();
        };
        try {
            inputStreamReader.read(inputBuffer);
        } catch (IOException e) {
            Log.e("Error", "Input Stream Reader Failed To Read Input Buffer");
            e.printStackTrace();
        }
        String data = new String(inputBuffer);
        try {
            inputStreamReader.close();
            this.fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            Log.e("Error: ", "XML Pull Parser Factory Failed To Create New Instance");
            e.printStackTrace();
        }
        factory.setNamespaceAware(true);
        XmlPullParser xpp = null;
        try {
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e) {
            Log.e("Error: ", "Factory Failed To Create New PuLL Parser");
            e.printStackTrace();
        }
        try {
            xpp.setInput(new StringReader(data));
        } catch (XmlPullParserException e) {
            Log.e("Error: ", "Pull Parser Failed to Read Data");
            e.printStackTrace();
        }
        int eventType = 0;
        try {
            eventType = xpp.getEventType();
        } catch (XmlPullParserException e) {
            Log.e("Error: ", "Failed To Get Event Type");
            e.printStackTrace();
        }
        try {
            String uuid = null;
            Subscription subscription = null;
            while ((eventType = xpp.next()) != XmlPullParser.END_DOCUMENT){
//                if (eventType == XmlPullParser.START_DOCUMENT) {
//                    System.out.println("Start document");
//                }
                if (eventType == XmlPullParser.START_TAG) {
                    uuid = xpp.getAttributeName(0);
//                    System.out.println("Start tag "+xpp.getName());
                }
                else if (eventType == XmlPullParser.END_TAG) {
                    subscription = null;
//                    System.out.println("End tag "+xpp.getName());
                }
                else if(eventType == XmlPullParser.TEXT) {
                    subscription = this.decodeRow(xpp.getText(), uuid);
                    book.addSubscription(subscription);
                }
            }
        } catch (XmlPullParserException e) {
            Log.e("Error: ", "Pull Parser Failed");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error: ", "Pull Parser Connection Failed");
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Boolean exists(String fpath) {
        File file = new File(fpath);
        return file.exists();
    }
}
