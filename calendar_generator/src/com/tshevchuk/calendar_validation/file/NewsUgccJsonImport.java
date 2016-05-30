package com.tshevchuk.calendar_validation.file;

import com.google.gson.stream.JsonReader;
import com.tshevchuk.calendar_validation.domain.DayInfo;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by taras on 09.05.16.
 */
public class NewsUgccJsonImport {
    public List<DayInfo> jsonToDayInfo(String json) throws IOException, InvalidFormatException {
        StringReader stringReader = new StringReader(json);
        JsonReader reader = new JsonReader(stringReader);
        List<DayInfo> days = new ArrayList<>();

        reader.beginObject();
        {
            if (!"columns".equals(reader.nextName())) {
                throw new InvalidFormatException();
            }
            reader.beginArray();
            {
                List<String> columns = new ArrayList<>();
                while (reader.hasNext()) {
                    columns.add(reader.nextString());
                }
                if (!columns.equals(Arrays.asList("date", "pist", "redDay", "day", "person"))) {
                    throw new InvalidFormatException();
                }
            }
            reader.endArray();

            if (!"data".equals(reader.nextName())) {
                throw new InvalidFormatException();
            }

            reader.beginArray();
            {
                while (reader.hasNext()) {
                    reader.beginArray();
                    {
                        DayInfo di = new DayInfo();
                        di.setDate(String.valueOf(reader.nextInt()));
                        di.setPist(reader.nextInt());
                        di.setRedDay(reader.nextInt() != 0);
                        di.setDay(reader.nextString());
                        di.setPerson(reader.nextString());
                        days.add(di);
                    }
                    reader.endArray();
                }
            }
            reader.endArray();
        }
        reader.endObject();

        return days;
    }
}
