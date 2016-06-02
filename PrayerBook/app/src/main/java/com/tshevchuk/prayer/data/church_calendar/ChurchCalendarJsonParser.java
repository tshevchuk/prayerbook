package com.tshevchuk.prayer.data.church_calendar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 22.05.16.
 */
public class ChurchCalendarJsonParser {
    private final JSONObject json;

    public ChurchCalendarJsonParser(String json) throws JSONException {
        this.json = new JSONObject(json);
    }

    public String getNonMovableDay(int month, int day) throws JSONException {
        JSONObject nonMovable = json.getJSONObject("non_movable");
        JSONObject monthJson = nonMovable.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return null;
        }
        JSONObject dayOfMonth = monthJson.optJSONObject(String.valueOf(day));
        if(dayOfMonth == null){
            return null;
        }
        return dayOfMonth.optString("day", null);
    }

    public String getNonMovablePerson(int month, int day) throws JSONException {
        JSONObject nonMovable = json.getJSONObject("non_movable");
        JSONObject monthJson = nonMovable.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return null;
        }
        JSONObject dayOfMonth = monthJson.optJSONObject(String.valueOf(day));
        if(dayOfMonth == null){
            return null;
        }
        return dayOfMonth.optString("person", null);
    }

    public boolean isNonMovableDayRed(int month, int dayOfMonth) throws JSONException {
        JSONObject nonMovable = json.getJSONObject("non_movable");
        JSONObject monthJson = nonMovable.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return false;
        }
        JSONObject dayOfMonthJson = monthJson.optJSONObject(String.valueOf(dayOfMonth));
        if(dayOfMonthJson == null){
            return false;
        }
        return dayOfMonthJson.optBoolean("isRed", false);
    }

    public String getNonMovableDayPist(int month, int dayOfMonth) throws JSONException {
        JSONObject nonMovable = json.getJSONObject("non_movable");
        JSONObject monthJson = nonMovable.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return null;
        }
        JSONObject dayOfMonthJson = monthJson.optJSONObject(String.valueOf(dayOfMonth));
        if(dayOfMonthJson == null){
            return null;
        }
        return dayOfMonthJson.optString("pist", null);
    }

    public String getMovableDay(int shiftFromEasterDays) throws JSONException {
        JSONObject movable = json.getJSONObject("movable");
        JSONObject date = movable.optJSONObject(String.valueOf(shiftFromEasterDays));
        if(date == null){
            return null;
        }
        return date.optString("day", null);
    }

    public boolean isMovableDayRed(int shiftFromEasterDays) throws JSONException {
        JSONObject movable = json.getJSONObject("movable");
        JSONObject date = movable.optJSONObject(String.valueOf(shiftFromEasterDays));
        if(date == null){
            return false;
        }
        return date.optBoolean("isRed", false);
    }

    public String getMovableDayPist(int shiftFromEasterDays) throws JSONException {
        JSONObject movable = json.getJSONObject("movable");
        JSONObject date = movable.optJSONObject(String.valueOf(shiftFromEasterDays));
        if(date == null){
            return null;
        }
        return date.optString("pist", null);
    }

    public List<Pist> getPosty() throws JSONException {
        List<Pist> posty = new ArrayList<>();
        JSONArray postyJson = json.getJSONArray("posty");
        for(int i = 0; i < postyJson.length(); ++i){
            JSONObject pistJson = postyJson.getJSONObject(i);
            Pist pist = new Pist();
            pist.setName(pistJson.getString("name"));
            pist.setType(pistJson.getString("type"));
            if(pistJson.has("fromMovable")){
                pist.setFromMovable(pistJson.getInt("fromMovable"));
            }else{
                String date = pistJson.optString("fromDate", null);
                String[] parts = date.split("\\.");
                int day = Integer.valueOf(parts[0].replaceAll("^0+", ""));
                int month = Integer.valueOf(parts[1].replaceAll("^0+", ""));
                pist.setFromNonMovableDate(month, day);
            }
            if(pistJson.has("toMovable")){
                pist.setToMovable(pistJson.getInt("toMovable"));
            }else{
                String date = pistJson.optString("toDate", null);
                String[] parts = date.split("\\.");
                int day = Integer.valueOf(parts[0].replaceAll("^0+", ""));
                int month = Integer.valueOf(parts[1].replaceAll("^0+", ""));
                pist.setToNonMovableDate(month, day);
            }
            posty.add(pist);
        }
        return posty;
    }

    public String getFixDay(int year, int month, int day) throws JSONException {
        JSONObject fixes = json.getJSONObject("fixes");
        JSONObject yearJson = fixes.optJSONObject(String.valueOf(year));
        if (yearJson == null) {
            return null;
        }
        JSONObject monthJson = yearJson.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return null;
        }
        JSONObject dayOfMonth = monthJson.optJSONObject(String.valueOf(day));
        if(dayOfMonth == null){
            return null;
        }
        return dayOfMonth.optString("day", null);
    }

    public String getFixPerson(int year, int month, int day) throws JSONException {
        JSONObject fixes = json.getJSONObject("fixes");
        JSONObject yearJson = fixes.optJSONObject(String.valueOf(year));
        if (yearJson == null) {
            return null;
        }
        JSONObject monthJson = yearJson.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return null;
        }
        JSONObject dayOfMonth = monthJson.optJSONObject(String.valueOf(day));
        if(dayOfMonth == null){
            return null;
        }
        return dayOfMonth.optString("person", null);
    }

    public String[] getEasterDates() throws JSONException {
        JSONArray easterDates = json.getJSONArray("easterDates");
        String[] dates = new String[easterDates.length()];
        for(int i = easterDates.length()-1; i >= 0; i--){
            dates[i] = easterDates.getString(i);
        }
        return dates;
    }
}
