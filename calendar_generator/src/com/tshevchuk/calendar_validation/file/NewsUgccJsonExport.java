package com.tshevchuk.calendar_validation.file;

import com.google.gson.stream.JsonWriter;
import com.tshevchuk.calendar_validation.domain.DayInfo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by taras on 09.05.16.
 */
public class NewsUgccJsonExport {
    public String calendarToJson(List<DayInfo> dayInfo) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        writer.beginObject();
        {
            writer.name("columns").beginArray().value("date").value("pist")
                    .value("redDay").value("day").value("person").endArray();
            writer.name("data");
            writer.beginArray();
            for (DayInfo di : dayInfo) {
                writer.beginArray();
                {
                    writer.value(Integer.valueOf(di.getDate().replaceAll("-", "")));
                    writer.value(di.getPist());
                    writer.value(di.isRedDay() ? 1 : 0);
                    writer.value(di.getDay());
                    writer.value(di.getPerson());
                }
                writer.endArray();
            }
            writer.endArray();
        }
        writer.endObject();

        writer.close();
        return stringWriter.toString();
    }
}
