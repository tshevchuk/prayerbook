package com.tshevchuk.calendar_validation.utils;

import java.io.IOException;

/**
 * Created by taras on 19.05.16.
 */
public interface CalendarConfigReader {
    String readConfig(String fileName) throws IOException;
}
