package com.tshevchuk.calendar_validation.parsers;

import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 25.05.16.
 */
public class DyvensvitParser implements Parser {
    private final static int COL_DAY_OF_MONTH = 0;
    private final static int COL_DAY_OF_WEEK = 1;
    private final static int COL_SVYATO = 2;
    private final static int COL_PIST = 3;
    private final static int COL_DAY = 4;
    private final static int COL_CHYTANNYA = 5;

    @Override
    public List<DayInfo> parse(int year, int month) throws IOException {
        String filePath = String.format("files/dyvensvit/%04d/%02d/c1.txt", year, month);
        String content = FileUtils.readFile(filePath);
        List<DayInfo> days = new ArrayList<>();
        for(String line : content.split("\n")){
            String[] data = line.split("\\|");
            DayInfo di = new DayInfo();
            di.setDate(DateUtils.dateToDateStr(year, month, Integer.valueOf(data[COL_DAY_OF_MONTH])));
            switch(Integer.valueOf(data[COL_PIST])){
                case 0:
                    di.setPist(DayInfo.PIST_NONE);
                    break;
                case 1:
                    di.setPist(DayInfo.PIST_PIST);
                    break;
                case 2:
                    di.setPist(DayInfo.PIST_STROHYY_PIST);
                    break;
                case 3:
                    di.setPist(DayInfo.PIST_ZAHALNYTSYA);
                    break;
                default:
                    throw new IllegalStateException("Wrong value for PIST " + data[COL_PIST]);
            }
            di.setDay(data[COL_DAY]);
            days.add(di);
        }

        return days;
    }
}
