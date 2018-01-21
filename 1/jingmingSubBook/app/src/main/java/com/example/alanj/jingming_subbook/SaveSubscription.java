package com.example.alanj.jingming_subbook;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import android.util.Xml;
import java.util.UUID;

/**
 * Created by AlanJ on 2018-01-18.
 */

public class SaveSubscription extends Subscription {
    protected FileOutputStream fileOutputStream;
    public SaveSubscription() {
        this.subBook = new SubBook();
    }
    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }
    private String concatSubBookData() {
        return this.subBook.getName() + "|" + this.subBook.getDate() + "|" +
                subBook.getMonthlyCharges() + "|" + this.subBook.getComment();
    }
    private Boolean exists() {
        return true;
    }
    public void SaveSub() {
        if (this.fileOutputStream == null) {
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
        UUID randomUUIDuid = UUID.randomUUID();
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", false);
            xmlSerializer.startTag(null, randomUUIDuid.toString());
            xmlSerializer.text(this.concatSubBookData());
            xmlSerializer.endTag(null,randomUUIDuid.toString());
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            this.fileOutputStream.write(dataWrite.getBytes());
            this.fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
