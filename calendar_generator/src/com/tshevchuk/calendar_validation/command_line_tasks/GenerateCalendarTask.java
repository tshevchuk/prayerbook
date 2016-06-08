package com.tshevchuk.calendar_validation.command_line_tasks;

import com.google.gson.stream.JsonWriter;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.FileUtils;
import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by taras on 16.05.16.
 */
public class GenerateCalendarTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--gen-calendar";
    }

    @Override
    public String getDescription() {
        return "--gen-calendar" +
                "\n   Згенерувати і вивести церковний календар";
    }

    @Override
    public void execute(String[] args) throws IOException {
        for (int year : new ChurchCalendar(new JvmCalendarConfigReader()).getCalendarYears()) {
            StringWriter stringWriter = new StringWriter();
            JsonWriter writer = new JsonWriter(stringWriter);
            writer.setIndent("  ");

            writer.beginArray();

            Map<String, CalendarDateInfo> days = generateCalendar(year, year);
            for (CalendarDateInfo dayInfo : days.values()) {
                writer.beginObject();
                {
                    writer.name("dateGregorian").value(DateUtils.dateToDateStr(dayInfo.getDate()));
                    writer.name("dateJulian").value(DateUtils.dateToDateStr(dayInfo.getDateJulian()));
                    writer.name("dayOfWeek").value(DateUtils.getDayOfWeekStr(dayInfo.getDate()));
                    writer.name("shiftDaysFromEaster").value(dayInfo.getShiftDaysFromEaster());
                    writer.name("day").value(dayInfo.getDay());
                    writer.name("person").value(dayInfo.getPerson());
                    writer.name("isRed").value(dayInfo.isDateRed());
                    writer.name("pistType").value(dayInfo.getPistType());
                    writer.name("pistName").value(dayInfo.getPistName());
                }
                writer.endObject();
            }

            writer.endArray();
            writer.close();

            String fileName = "files/generated/generated-calendar-" + year + ".json";
            FileUtils.writeFile(fileName, stringWriter.toString());
            System.out.println("Generated " + fileName);
        }
    }
}
