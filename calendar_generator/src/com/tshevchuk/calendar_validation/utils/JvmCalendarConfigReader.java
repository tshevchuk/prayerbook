package com.tshevchuk.calendar_validation.utils;

import java.io.IOException;

/**
 * Created by taras on 19.05.16.
 */
public class JvmCalendarConfigReader implements CalendarConfigReader {
    @Override
    public String readConfig(String fileName) throws IOException {
        return FileUtils.readFile("files/config/" + fileName);
    }
}
