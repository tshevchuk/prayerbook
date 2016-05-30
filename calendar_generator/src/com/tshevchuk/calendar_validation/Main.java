package com.tshevchuk.calendar_validation;

import com.tshevchuk.calendar_validation.command_line_tasks.*;
import com.tshevchuk.calendar_validation.file.InvalidFormatException;

import java.io.IOException;
import java.util.*;

public class Main {
    private final static CommandLineTask[] tasks;

    static {
        tasks = new CommandLineTask[]{
                new GenerateCalendarTask(),
                new GenerateDaysTask(),
                new GeneratePersonsTask(),
                new GeneratePostyTask(),
                new GenerateRedDaysTask(),
                new ParseNewsUgccCalendarTask(),
                new PrintDaysNewsUgccTask(),
                new PrintEasterDatesTask(),
                new CompareNewsUgccPersonTask(),
                new CompareNewsUgccDayTask(),
                new CompareNewsUgccRedDayTask(),
                new CompareNewsUgccPostyTask(),
                new CompareDyvensvitPostyTask(),
        };
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        if (args.length > 0) {
            for (CommandLineTask task : tasks) {
                if (args[0].equals(task.getCommand())) {
                    String[] commandArgs = Arrays.asList(args).subList(1, args.length).toArray(new String[args.length - 1]);
                    task.execute(commandArgs);
                    return;
                }
            }
        }

        System.out.println("Формат виклику:");
        System.out.println("<app_name> <command_with_args>");
        System.out.println("де <command_with_args>:");

        for (CommandLineTask task : tasks) {
            System.out.println(task.getDescription());
        }
        System.out.println();
        System.out.println("<from_year>, <to_year> - напр 2012, 2016");
        System.out.println("<from_date>, <to_date> - напр 20120101 20160820");
    }
}
