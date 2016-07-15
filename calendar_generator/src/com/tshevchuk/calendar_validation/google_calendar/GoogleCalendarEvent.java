package com.tshevchuk.calendar_validation.google_calendar;

/**
 * Created by taras on 12.07.16.
 */
public class GoogleCalendarEvent {
    private String summary;
    private String description;
    //yyyy-mm-dd
    private String date;
    // calendar event color ids
    // https://drive.google.com/file/d/0B_rZIJGWcoyIS2tOMTdhTVFQSjg/view?usp=sharing
    // https://developers.google.com/google-apps/calendar/v3/reference/colors/get
    private int color;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "GoogleCalendarEvent{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", color=" + color +
                '}';
    }
}
