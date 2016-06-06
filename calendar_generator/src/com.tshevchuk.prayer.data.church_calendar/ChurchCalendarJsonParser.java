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
    private JSONObject nonMovableJson;
    private JSONObject[] nonMovableMonthes = new JSONObject[12];
    private JSONObject[] nonMovableDaysOfMonth = new JSONObject[12 * 32];
    private JSONObject movableJson;
    private JSONObject fixesJson;

    public ChurchCalendarJsonParser(String json) throws JSONException {
        this.json = new JSONObject(json);
    }

    public String getNonMovableDay(int month, int day) throws JSONException {
        JSONObject dayOfMonth = getNonMovableDayOfMonth(month, day);
        if (dayOfMonth == null) {
            return null;
        }
        return dayOfMonth.optString("day", null);
    }

    private JSONObject getNonMovableDayOfMonth(int month, int day) throws JSONException {
        int index = (month - 1) * 32 + day;
        if (nonMovableDaysOfMonth[index] == null) {
            JSONObject monthJson = getNonMovableMonth(month);
            if (monthJson == null) {
                return null;
            }
            nonMovableDaysOfMonth[index] = monthJson.optJSONObject(String.valueOf(day));

        }
        return nonMovableDaysOfMonth[index];
    }

    private JSONObject getNonMovableMonth(int month) throws JSONException {
        int index = month - 1;
        if (nonMovableMonthes[index] == null) {
            JSONObject nonMovable = getNonMovable();
            nonMovableMonthes[index] = nonMovable.optJSONObject(String.valueOf(month));
        }
        return nonMovableMonthes[index];
    }

    private JSONObject getNonMovable() throws JSONException {
        if (nonMovableJson == null) {
            nonMovableJson = json.getJSONObject("non_movable");
        }
        return nonMovableJson;
    }

    public String getNonMovablePerson(int month, int day) throws JSONException {
        JSONObject dayOfMonth = getNonMovableDayOfMonth(month, day);
        if (dayOfMonth == null) {
            return null;
        }
        return dayOfMonth.optString("person", null);
    }

    public boolean isNonMovableDayRed(int month, int dayOfMonth) throws JSONException {
        JSONObject dayOfMonthJson = getNonMovableDayOfMonth(month, dayOfMonth);
        return dayOfMonthJson != null && dayOfMonthJson.optBoolean("isRed", false);
    }

    public String getNonMovableDayPist(int month, int dayOfMonth) throws JSONException {
        JSONObject dayOfMonthJson = getNonMovableDayOfMonth(month, dayOfMonth);
        if (dayOfMonthJson == null) {
            return null;
        }
        return dayOfMonthJson.optString("pist", null);
    }

    public String getMovableDay(int shiftFromEasterDays) throws JSONException {
        JSONObject movable = getMovable();
        JSONObject date = movable.optJSONObject(String.valueOf(shiftFromEasterDays));
        if (date == null) {
            return null;
        }
        return date.optString("day", null);
    }

    public boolean isMovableDayRed(int shiftFromEasterDays) throws JSONException {
        JSONObject movable = getMovable();
        JSONObject date = movable.optJSONObject(String.valueOf(shiftFromEasterDays));
        if (date == null) {
            return false;
        }
        return date.optBoolean("isRed", false);
    }

    private JSONObject getMovable() throws JSONException {
        if (movableJson == null) {
            movableJson = json.getJSONObject("movable");
        }
        return movableJson;
    }

    public String getMovableDayPist(int shiftFromEasterDays) throws JSONException {
        JSONObject movable = getMovable();
        JSONObject date = movable.optJSONObject(String.valueOf(shiftFromEasterDays));
        if (date == null) {
            return null;
        }
        return date.optString("pist", null);
    }

    public List<Pist> getPosty() throws JSONException {
        List<Pist> posty = new ArrayList<>();
        JSONArray postyJson = json.getJSONArray("posty");
        for (int i = 0; i < postyJson.length(); ++i) {
            JSONObject pistJson = postyJson.getJSONObject(i);
            Pist pist = new Pist();
            pist.setName(pistJson.getString("name"));
            pist.setType(pistJson.getString("type"));
            if (pistJson.has("fromMovable")) {
                pist.setFromMovable(pistJson.getInt("fromMovable"));
            } else {
                String date = pistJson.optString("fromDate", null);
                String[] parts = date.split("\\.");
                int day = Integer.valueOf(parts[0].replaceAll("^0+", ""));
                int month = Integer.valueOf(parts[1].replaceAll("^0+", ""));
                pist.setFromNonMovableDate(month, day);
            }
            if (pistJson.has("toMovable")) {
                pist.setToMovable(pistJson.getInt("toMovable"));
            } else {
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
        JSONObject fixes = getFixes();
        JSONObject yearJson = fixes.optJSONObject(String.valueOf(year));
        if (yearJson == null) {
            return null;
        }
        JSONObject monthJson = yearJson.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return null;
        }
        JSONObject dayOfMonth = monthJson.optJSONObject(String.valueOf(day));
        if (dayOfMonth == null) {
            return null;
        }
        return dayOfMonth.optString("day", null);
    }

    private JSONObject getFixes() throws JSONException {
        if (fixesJson == null) {
            fixesJson = json.getJSONObject("fixes");
        }
        return fixesJson;
    }

    public String getFixPerson(int year, int month, int day) throws JSONException {
        JSONObject fixes = getFixes();
        JSONObject yearJson = fixes.optJSONObject(String.valueOf(year));
        if (yearJson == null) {
            return null;
        }
        JSONObject monthJson = yearJson.optJSONObject(String.valueOf(month));
        if (monthJson == null) {
            return null;
        }
        JSONObject dayOfMonth = monthJson.optJSONObject(String.valueOf(day));
        if (dayOfMonth == null) {
            return null;
        }
        return dayOfMonth.optString("person", null);
    }

    public String[] getEasterDates() throws JSONException {
        JSONArray easterDates = json.getJSONArray("easterDates");
        String[] dates = new String[easterDates.length()];
        for (int i = easterDates.length() - 1; i >= 0; i--) {
            dates[i] = easterDates.getString(i);
        }
        return dates;
    }
}
