package com.tshevchuk.calendar_validation.domain;

/**
 * Created by taras on 07.05.16.
 */
public class DayInfo {
    public static final int PIST_NONE = 0;
    public static final int PIST_PIST = 1;
    public static final int PIST_STROHYY_PIST = 2;
    public static final int PIST_ZAHALNYTSYA = 3;

    private int pist;
    private boolean redDay;
    private String date;
    private String day;
    private String person;

    public void setRedDay(boolean redDay) {
        this.redDay = redDay;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public boolean isRedDay() {
        return redDay;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getPerson() {
        return person;
    }

    public int getPist() {
        return pist;
    }

    public void setPist(int pist) {
        this.pist = pist;
    }

    public String getPistStr(){
        if(pist == PIST_NONE){
            return "none";
        } else if(pist == PIST_STROHYY_PIST){
            return "strohyjPist";
        } else if(pist==PIST_PIST){
            return "pist";
        } else if(pist==PIST_ZAHALNYTSYA){
            return "zahalnytsya";
        }
        return null;
    }

    @Override
    public String toString() {
        return "DayInfo{" +
                "pist=" + pist + " " + getPistStr() +
                ",\n    redDay=" + redDay +
                ",\n    date='" + date + '\'' +
                ",\n    day='" + day + '\'' +
                ",\n    person='" + person + '\'' +
                '}';
    }
}
