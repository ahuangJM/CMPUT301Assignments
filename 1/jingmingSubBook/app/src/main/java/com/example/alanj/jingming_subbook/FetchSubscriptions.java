package com.example.alanj.jingming_subbook;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by AlanJ on 2018-01-19.
 */

public class FetchSubscriptions extends Subscription {
    FileInputStream fileInputStream;
    public FetchSubscriptions() {
        this.subBook = new SubBook();
    }
    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }
    private SubBook splitSubBookData(String data) {
        String[] dataParts = data.split("\\|");
        SubBook subBook = new SubBook();
        try {
            subBook.setName(dataParts[0]);
            subBook.setDate(dataParts[1]);
            subBook.setMonthlyCharges(dataParts[2]);
            subBook.setComment(dataParts[3]);
        } catch (InputTooLongException e) {
            e.printStackTrace();
        } catch (NumberOutOfBoundException e) {
            e.printStackTrace();
        }
        return subBook;
    }
    private Boolean exists() {
        return true;
    }
    public ArrayList<SubBook> getSubs() {
        if (this.fileInputStream == null) {
            try {
                throw new FileObjectNotInitializedException();
            } catch (FileObjectNotInitializedException e) {
                e.printStackTrace();
            }
        }
        if (this.exists()) {
            try {
                throw new SubscriptionExistsAlreadyException();
            } catch (SubscriptionExistsAlreadyException e) {
                e.printStackTrace();
            }
        }
        String subscription;
        SubBook subBook = new SubBook();
        SubBook subBuff = new SubBook();
        String uuid;
        ArrayList<SubBook> subBooks = new ArrayList<>();
        XmlPullParserFactory factory = null;
        XmlPullParser xpp = null;
        int eventType = 0;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            char[] inputBuffer = new char[0];
            inputBuffer = new char[fileInputStream.available()];
            inputStreamReader.read(inputBuffer);
            String data = new String(inputBuffer);
            inputStreamReader.close();
            fileInputStream.close();

            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            eventType = xpp.getEventType();

            while ((eventType = xpp.next()) != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_DOCUMENT) {
//                    System.out.println("Start document");
                }
                else if (eventType == XmlPullParser.START_TAG) {
//                    System.out.println("Start tag "+xpp.getName());
                    uuid = xpp.getName();
                    subBook.setUuid(uuid);
                }
                else if (eventType == XmlPullParser.END_TAG) {
//                    System.out.println("End tag "+xpp.getName());
                    subBooks.add(subBook);
                    subBook = new SubBook();
                }
                else if(eventType == XmlPullParser.TEXT) {
                    subscription = xpp.getText();
                    subBuff = this.splitSubBookData(subscription);
                    subBook.setName(subBuff.getName());
                    subBook.setDate(subBuff.getDate());
                    subBook.setMonthlyCharges(subBuff.getMonthlyCharges());
                    subBook.setComment(subBuff.getComment());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InputTooLongException e) {
            e.printStackTrace();
        } catch (NumberOutOfBoundException e) {
            e.printStackTrace();
        }
        return subBooks;
    }
}
