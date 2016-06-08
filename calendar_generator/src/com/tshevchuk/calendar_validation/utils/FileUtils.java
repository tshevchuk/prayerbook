package com.tshevchuk.calendar_validation.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by taras on 09.05.16.
 */
public class FileUtils {
    public static void writeFile(String fileName, String content) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(content);
        }
    }

    public static String readFile(String fileName) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(fileName));
        return new String(encoded, "UTF-8");
    }
}
