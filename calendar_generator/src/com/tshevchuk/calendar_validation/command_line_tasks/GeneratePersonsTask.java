package com.tshevchuk.calendar_validation.command_line_tasks;

import com.google.gson.stream.JsonWriter;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.FileUtils;
import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;
import com.tshevchuk.calendar_validation.utils.TextUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by taras on 16.05.16.
 */
public class GeneratePersonsTask extends CommandLineTask{
    @Override
    public String getCommand() {
        return "--gen-persons";
    }

    @Override
    public String getDescription() {
        return "--gen-persons" +
                "\n   Згенерувати і вивести святих церковного календаря";
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
                if (!TextUtils.isEmpty(dayInfo.getPerson())) {
                    writer.name(DateUtils.dateToDateStr(dayInfo.getDate()));

                    writer.beginObject();

                    writer.name("person").value(dayInfo.getPerson());
                    writer.name("dayOfWeek").value(DateUtils.getDayOfWeekStr(dayInfo.getDate()));
                    writer.name("shiftDaysFromEaster").value(dayInfo.getShiftDaysFromEaster());

                    writer.endObject();
                }
            }

            writer.endObject();
            writer.close();

            String fileName = "files/generated/generated-persons-" + year + ".json";
            FileUtils.writeFile(fileName, stringWriter.toString());
            System.out.println("Generated " + fileName);
        }
    }
}
