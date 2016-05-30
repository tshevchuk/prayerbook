package com.tshevchuk.calendar_validation.file;

import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.calendar_validation.utils.FileUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by taras on 16.05.16.
 */
public class JsonFileUtils {
    public static List<DayInfo> readDayInfosFromJson(int year) throws IOException, InvalidFormatException {
        String json = FileUtils.readFile("files/news_ugcc/news_ugcc_calendar_" + year + ".json");
        return new NewsUgccJsonImport().jsonToDayInfo(json);
    }
}
