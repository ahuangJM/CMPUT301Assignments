package com.example.alanj.jingming_subbook;

import java.util.Date;
/**
 * Created by AlanJ on 2018-01-18.
 */

public class SubBook {
    private String uuid;
    private String name;
    private String date; // started date
    private String monthlyCharges;
    private String comment;
    /**
     * Date is automatically set to today
     * */
    public SubBook() {
        this.date = (new Date()).toString();
    }
    public void setUuid(String uuid) {this.uuid = uuid;}
    public String getUuid() {return this.uuid;}
    public void setName(String name) throws InputTooLongException {
        if (name.length() > 20) {
            throw new InputTooLongException();
        }
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setMonthlyCharges(String monthlyCharges) throws NumberOutOfBoundException {
        if (Double.parseDouble(monthlyCharges) < 0) {
            throw new NumberOutOfBoundException();
        }
        this.monthlyCharges = monthlyCharges;
    }
    public void setComment(String comment) throws InputTooLongException {
        if (name.length() > 30) {
            throw new InputTooLongException();
        }
        this.comment = comment;
    }

    public String getName() {
        return this.name;
    }

    public String getComment() {
        return this.comment;
    }

    public String getMonthlyCharges() {
        return this.monthlyCharges;
    }

    public String getDate() {
        return this.date;
    }
}
