package com.tshevchuk.calendar_validation.parsers;

import com.tshevchuk.calendar_validation.domain.DayInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by taras on 09.05.16.
 */
public interface Parser {
    List<DayInfo> parse(int year, int month) throws IOException;
}
