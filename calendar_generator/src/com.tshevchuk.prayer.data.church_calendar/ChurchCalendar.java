package com.tshevchuk.prayer.data.church_calendar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;

public class ChurchCalendar {
    public static final String FILE_NAME_CALENDAR_DAYS_JSON = "church_calendar.json";
    private final CalendarConfigReader configReader;
    private ChurchCalendarJsonParser churchCalendarJsonParser;
    private List<Pist> posty;
    private CalendarDate lastEasterJulianDate;

    public ChurchCalendar(CalendarConfigReader calendarConfigReader) {
        configReader = calendarConfigReader;
    }

    public synchronized int[] getCalendarYears() throws IOException, JSONException {
        readCalendarConfig();
        String[] dates = churchCalendarJsonParser.getEasterDates();
        int years[] = new int[dates.length];
        for (int i = dates.length - 1; i >= 0; i--) {
            years[i] = Integer.valueOf(dates[i].substring(0, 4));
        }
        return years;
    }

    public synchronized CalendarDate getEasterDateJulian(int year) throws IOException, JSONException {
        readCalendarConfig();
        if (lastEasterJulianDate != null && lastEasterJulianDate.getYear() == year) {
            return lastEasterJulianDate;
        }

        //format: 2003-04-27
        String[] dates = churchCalendarJsonParser.getEasterDates();
        for (int i = dates.length - 1; i >= 0; i--) {
            int y = Integer.valueOf(dates[i].substring(0, 4));
            if (y == year) {
                CalendarDate calendarDate = new CalendarDate(y, Integer.valueOf(dates[i].substring(5, 7)),
                        Integer.valueOf(dates[i].substring(8)));
                lastEasterJulianDate = calendarDate;
                return calendarDate;
            }
        }
        return null;
    }

    public synchronized ArrayList<CalendarDateInfo> getCalendarDays(int year) throws IOException, JSONException {
        readCalendarConfig();

        int daysCount = new GregorianCalendar().isLeapYear(year) ? 366 : 365;
        Calendar julianCal = Calendar.getInstance();
        julianCal.clear();

        ArrayList<CalendarDateInfo> calendarDays = new ArrayList<>(daysCount);
        int easterDayOfYear = getEasterDayOfYear(year);

        Calendar[] cachedJulian = new Calendar[13];
        Calendar cachedGreg = null;

        for (int i = 1; i <= daysCount; ++i) {
            if(cachedGreg == null){
                cachedGreg = Calendar.getInstance();
                cachedGreg.clear();
                cachedGreg.set(Calendar.YEAR, year);
            }
            cachedGreg.set(Calendar.DAY_OF_YEAR, i);

            int cacheIndex = i % 13;
            Calendar jul = cachedJulian[cacheIndex];
            if(jul == null){
                jul = julianCal;
                jul.setTime(cachedGreg.getTime());
                jul.add(Calendar.DAY_OF_MONTH, -13);
            }

            calendarDays.add(getCalendarDay(cachedGreg, julianCal, easterDayOfYear));

            cachedGreg = cachedJulian[cacheIndex];
            cachedJulian[cacheIndex]=cachedGreg;
        }
        return calendarDays;
    }

    public synchronized CalendarDateInfo getCalendarDay(Date date) throws IOException, JSONException {
        readCalendarConfig();

        Calendar gregCal = Calendar.getInstance();
        gregCal.setTime(date);
        int yearHreh = gregCal.get(Calendar.YEAR);

        Calendar julianCal = Calendar.getInstance();
        julianCal.setTime(date);
        julianCal.add(Calendar.DAY_OF_MONTH, -13);

        return getCalendarDay(gregCal, julianCal, getEasterDayOfYear(yearHreh));
    }

    private CalendarDateInfo getCalendarDay(Calendar gregCal, Calendar julianCal, int easterDayOfYear) throws IOException, JSONException {
        int dayHreh = gregCal.get(Calendar.DAY_OF_MONTH);
        int monthHreh = gregCal.get(Calendar.MONTH) + 1;
        int yearHreh = gregCal.get(Calendar.YEAR);
        int dayOfWeek = gregCal.get(Calendar.DAY_OF_WEEK);
        Date gregDate = gregCal.getTime();

        int dayJulian = julianCal.get(Calendar.DAY_OF_MONTH);
        int monthJulian = julianCal.get(Calendar.MONTH) + 1;
        int yearJulian = julianCal.get(Calendar.YEAR);
        int dayOfYearJulian = julianCal.get(Calendar.DAY_OF_YEAR);
        if (yearJulian < yearHreh && monthJulian == 12) {
            dayOfYearJulian = dayJulian - 31;
        }
        Date dateJulian = julianCal.getTime();

        int easterShiftDays = dayOfYearJulian - easterDayOfYear;
        String day = getCalendarDay(yearJulian, monthJulian, dayJulian, dayOfWeek, easterShiftDays);
        boolean isDateRed = isRedDate(yearJulian, monthJulian, dayJulian, dayOfWeek, easterShiftDays);
        String person = getCalendarPerson(yearJulian, monthJulian, dayJulian, dayOfWeek, easterShiftDays);
        String pist = getPistType(yearJulian, monthJulian, dayJulian, dayOfWeek, easterShiftDays);
        String pistName = getPistName(yearJulian, monthJulian, dayJulian, dayOfWeek, easterShiftDays);
        CalendarDateInfo calendarDateInfo = new CalendarDateInfo(gregDate, dateJulian, day, person, isDateRed, easterShiftDays, pist, pistName);
        calendarDateInfo.setDayDescription(getDayDescription(day, person));
        return calendarDateInfo;

    }

    private int getEasterDayOfYear(int yearGreg) throws IOException, JSONException {
        CalendarDate easterDate = getEasterDateJulian(yearGreg);
        Calendar calVelykden = Calendar.getInstance();
        calVelykden.clear();
        calVelykden.set(easterDate.getYear(), easterDate.getMonth() - 1, easterDate.getDay());
        return calVelykden.get(Calendar.DAY_OF_YEAR);
    }

    private String getDayDescription(String day, String person) {
        StringBuilder sb = new StringBuilder();
        if (day != null) {
            sb.append(day);
        }
        sb.append(day == null || day.isEmpty() || person == null || person.isEmpty() ? "" : "<br>");
        if (person != null) {
            sb.append(person);
        }
        return sb.toString().replace("<r>", "<font color=\"red\">").replace("</r>", "</font>");
    }

    private String getCalendarDay(int year, int month, int dayOfMonth, int dayOfWeek, int easterShiftDays) throws IOException, IllegalFormatException, JSONException {
        String fix = churchCalendarJsonParser.getFixDay(year, month, dayOfMonth);
        String movable = churchCalendarJsonParser.getMovableDay(easterShiftDays);
        String nonMovable = churchCalendarJsonParser.getNonMovableDay(month, dayOfMonth);
        StringBuilder sb = new StringBuilder();
        if (nonMovable != null) {
            sb.append(nonMovable);
        }
        if (nonMovable != null && !nonMovable.isEmpty()
                && movable != null && !movable.isEmpty()) {
            sb.append(", ");
        }
        if (movable != null) {
            sb.append(movable);
        }

        String day = sb.toString();
        if (fix != null) {
            if (fix.equals(day)) {
                throw new IllegalStateException(String.format(Locale.US,
                        "Fix and calculated day are equal %d-%d-%d %s", year, month, dayOfMonth, fix));
            }
            return fix;
        }
        return day;
    }

    private String getCalendarPerson(int year, int month, int dayOfMonth, int dayOfWeek, int easterShiftDays) throws IOException, JSONException {
        String person = churchCalendarJsonParser.getFixPerson(year, month, dayOfMonth);
        if (person == null) {
            person = churchCalendarJsonParser.getNonMovablePerson(month, dayOfMonth);
        }
        return (person == null || person.isEmpty()) ? null : person;
    }

    private boolean isRedDate(int year, int month, int dayOfMonth, int dayOfWeek, int easterShiftDays) throws IOException, JSONException {
        if (dayOfWeek == Calendar.SUNDAY) {
            return true;
        }
        return churchCalendarJsonParser.isNonMovableDayRed(month, dayOfMonth)
                || churchCalendarJsonParser.isMovableDayRed(easterShiftDays);
    }

    private void readCalendarConfig() throws IOException, JSONException {
        if (churchCalendarJsonParser == null) {
            churchCalendarJsonParser = new ChurchCalendarJsonParser(configReader.readConfig(FILE_NAME_CALENDAR_DAYS_JSON));
        }
    }

    private String getPistType(int year, int month, int dayOfMonth, int dayOfWeek, int easterShiftDays) throws IOException, IllegalFormatException, JSONException {
        if (posty == null) {
            posty = churchCalendarJsonParser.getPosty();
        }
        Pist pist = getPist(month, dayOfMonth, easterShiftDays);

        String movablePistType = churchCalendarJsonParser.getMovableDayPist(easterShiftDays);
        String nonMovablePistType = churchCalendarJsonParser.getNonMovableDayPist(month, dayOfMonth);
        String pistType = null;

        pistType = movablePistType;
        if (pistType == null) {
            pistType = nonMovablePistType;
        }
        if (pist != null) {
            if (pist.getType().equals(movablePistType)) {
                throw new IllegalStateException("Same type for pist in posty and in movable sections "
                        + String.format(Locale.US, "%d-%02d-%02d", year, month, dayOfMonth)
                        + " shift: " + easterShiftDays + "; " + pist);
            }
            if (pist.getType().equals(nonMovablePistType)) {
                throw new IllegalStateException("Same type for pist in posty and in non-movable sections "
                        + String.format(Locale.US, "%d-%02d-%02d", year, month, dayOfMonth)
                        + " shift: " + easterShiftDays + "; " + pist);
            }
            if (pistType == null) {
                pistType = pist.getType();
            }
            if (CalendarDateInfo.PIST_STROHYY.equals(nonMovablePistType)
                    || CalendarDateInfo.PIST_STROHYY.equals(movablePistType)) {
                pistType = CalendarDateInfo.PIST_STROHYY;
            }
        }
        if (pistType == null && dayOfWeek == Calendar.FRIDAY) {
            pistType = CalendarDateInfo.PIST_PIST;
        }
        return (pistType == null || pistType.isEmpty()) ? null : pistType;
    }

    private Pist getPist(int month, int dayOfMonth, int easterShiftDays) throws IOException {
        Pist pist = null;
        for (Pist p : posty) {
            boolean matchFrom;
            boolean matchTo;
            if (p.getFromMovable() != null) {
                matchFrom = easterShiftDays >= p.getFromMovable();
            } else {
                matchFrom = month > p.getFromNonMovableMonth()
                        || (month == p.getFromNonMovableMonth() && dayOfMonth >= p.getFromNonMovableDayOfMonth());
            }
            if (p.getToMovable() != null) {
                matchTo = easterShiftDays <= p.getToMovable();
            } else {
                matchTo = month < p.getToNonMovableMonth()
                        || (month == p.getToNonMovableMonth() && dayOfMonth <= p.getToNonMovableDayOfMonth());
            }
            if (matchFrom && matchTo) {
                pist = p;
                break;
            }
        }
        return pist;
    }

    private String getPistName(int year, int month, int dayOfMonth, int dayOfWeek, int easterShiftDays) throws IOException {
        Pist pist = getPist(month, dayOfMonth, easterShiftDays);
        if (pist != null) {
            return pist.getName();
        }
        return null;
    }
}