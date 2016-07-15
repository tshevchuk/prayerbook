package com.tshevchuk.calendar_validation.command_line_tasks;

import com.google.api.services.calendar.model.Event;
import com.tshevchuk.calendar_validation.file.InvalidFormatException;
import com.tshevchuk.calendar_validation.google_calendar.GoogleCalendar;
import com.tshevchuk.calendar_validation.google_calendar.GoogleCalendarEvent;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;

/**
 * Created by taras on 13.07.16.
 */
public class ExportToGoogleCalendarTask extends CommandLineTask {
    public static final String CHURCH_CALENDAR = "d2nbvpamlbiutto44njllbuqfg@group.calendar.google.com";
    public static final String CHURCH_CALENDAR_SVYATA = "2tdql4rt2fnfdadv967qb8890g@group.calendar.google.com";
    public static final String CHURCH_CALENDAR_POSTY = "9v90l378dk2fk8lv877jq12ig4@group.calendar.google.com";

    @Override
    public String getCommand() {
        return "--export-to-google-calendar";
    }

    @Override
    public String getDescription() {
        return "--export-to-google-calendar" +
                "\n   Експортувати календарі до Google Calendars";
    }

    @Override
    public void execute(String[] args) throws IOException, InvalidFormatException {
        exportChurchCalendar(CHURCH_CALENDAR, new CalendarEventCreator() {
            @Override
            public GoogleCalendarEvent createEvent(CalendarDateInfo day) {
                final GoogleCalendarEvent event = new GoogleCalendarEvent();
                event.setSummary(stripHtmlTags(day.getDayDescription()));
                event.setColor(day.isDateRed() ? 11 : 8);
                event.setDate(dateToStr(day.getDate()));
                event.setDescription("Більше інформації про календар: https://goo.gl/njx89P");
                return event;
            }
        });
        exportChurchCalendar(CHURCH_CALENDAR_SVYATA, new CalendarEventCreator() {
            @Override
            public GoogleCalendarEvent createEvent(CalendarDateInfo day) {
                if (!day.isDateRed()) {
                    return null;
                }
                final GoogleCalendarEvent event = new GoogleCalendarEvent();
                event.setSummary(stripHtmlTags(day.getDayDescription()));
                event.setColor(11);
                event.setDate(dateToStr(day.getDate()));
                event.setDescription("Більше інформації про календар: https://goo.gl/njx89P");
                return event;
            }
        });
        exportChurchCalendar(CHURCH_CALENDAR_POSTY, new CalendarEventCreator() {
            @Override
            public GoogleCalendarEvent createEvent(CalendarDateInfo day) {
                if (day.getPistType() == null) {
                    return null;
                }
                final GoogleCalendarEvent event = new GoogleCalendarEvent();
                if (day.getPistType().equals(CalendarDateInfo.PIST_PIST)) {
                    event.setColor(3);
                    event.setSummary("Піст");
                } else if (day.getPistType().equals(CalendarDateInfo.PIST_STROHYY)) {
                    event.setColor(4);
                    event.setSummary("Строгий піст");
                } else if (day.getPistType().equals(CalendarDateInfo.PIST_ZAHALNYTSYA)) {
                    event.setColor(1);
                    event.setSummary("Загальниця");
                }
                if (day.getPistName() != null) {
                    event.setDescription(day.getPistName() + "\n\n" + "Більше інформації про календар: https://goo.gl/njx89P");
                } else {
                    event.setDescription("Більше інформації про календар: https://goo.gl/njx89P");
                }
                event.setDate(dateToStr(day.getDate()));
                return event;
            }
        });
    }

    private void exportChurchCalendar(String calId, CalendarEventCreator creator) throws IOException {
        final GoogleCalendar calendar = new GoogleCalendar();
        final List<CalendarDateInfo> days = generateCalendar();

        System.out.println("Removing old calendars...");
        List<Event> events = calendar.getAllEventsFromCalendar(calId);
        for (final Event event : events) {
            calendar.removeCalendarEvent(calId, event);
            System.out.println("removed event " + event.getId());
        }

        System.out.println("events to create: " + days.size());
        for (CalendarDateInfo day : days) {
            GoogleCalendarEvent event = creator.createEvent(day);
            if (event != null) {
                calendar.addEvent(calId, event);
            }
        }
        System.out.println("Done");
    }

    private List<CalendarDateInfo> generateCalendar() throws IOException {
        List<CalendarDateInfo> days = new ArrayList<>();
        // for (int year : new ChurchCalendar(new JvmCalendarConfigReader()).getCalendarYears()) {
        Map<String, CalendarDateInfo> daysMap = generateCalendar(2016, 2020);
        for (CalendarDateInfo dayInfo : daysMap.values()) {
            days.add(dayInfo);
        }
        //}
        return days;
    }

    private String dateToStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.format("%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }

    private String stripHtmlTags(String str) {
        return Jsoup.parse(str).text();
    }

    private interface CalendarEventCreator {
        GoogleCalendarEvent createEvent(CalendarDateInfo day);
    }
}
