package com.tshevchuk.calendar_validation.command_line_tasks;

import com.google.gson.stream.JsonWriter;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.FileUtils;
import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by taras on 26.05.16.
 */
public class GeneratePostyTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--gen-posty";
    }

    @Override
    public String getDescription() {
        return "--gen-posty" +
                "\n   Згенерувати і вивести пости церковного календаря";
    }

    @Override
    public void execute(String[] args) throws IOException {
        for (int year : new ChurchCalendar(new JvmCalendarConfigReader()).getCalendarYears()) {
            StringWriter stringWriter = new StringWriter();
            JsonWriter writer = new JsonWriter(stringWriter);
            writer.setIndent("  ");

            writer.beginObject();

            Map<String, CalendarDateInfo> days = generateCalendar(year, year);
            for (CalendarDateInfo dayInfo : days.values()) {
                if (dayInfo.getPistType() != null || dayInfo.getPistName() != null) {
                    writer.name(DateUtils.dateToDateStr(dayInfo.getDate()));

                    writer.beginObject();

                    writer.name("type").value(dayInfo.getPistType());
                    writer.name("name").value(dayInfo.getPistName());
                    writer.name("dayOfWeek").value(DateUtils.getDayOfWeekStr(dayInfo.getDate()));
                    writer.name("shiftDaysFromEaster").value(dayInfo.getShiftDaysFromEaster());

                    writer.endObject();
                }
            }

            writer.endObject();
            writer.close();

            String fileName = "files/generated/generated-posty-" + year + ".json";
            FileUtils.writeFile(fileName, stringWriter.toString());
            System.out.println("Generated " + fileName);
        }
    }
}
