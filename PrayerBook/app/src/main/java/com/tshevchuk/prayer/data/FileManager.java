package com.tshevchuk.prayer.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by taras on 16.04.16.
 */
public class FileManager {
    public static final String ERROR_REPORTS_ATTACHMENTS = "error_reports_attachments";
    private static final String TAG = FileManager.class.getName();
    private final Context context;


    public FileManager(Context context) {
        this.context = context;
    }

    public File storeErrorReportScreenshot(byte[] screenshot) {
        return storeFile(ERROR_REPORTS_ATTACHMENTS,
                "screenshot_" + System.currentTimeMillis() + ".png",
                screenshot);
    }

    public File storeErrorReportDeviceInfoAttachment(String s) {
        return storeFile(ERROR_REPORTS_ATTACHMENTS,
                "device_info_" + System.currentTimeMillis() + ".txt",
                s.getBytes());
    }

    private File storeFile(String dirName, String fileName, byte[] content) {
        File dir = new File(context.getCacheDir(), dirName);
        if (!dir.exists() && !dir.mkdirs()) {
            Log.d(TAG, "Can't create directory");
            return null;
        }

        File deviceInfoFile = new File(dir, fileName);

        try {
            OutputStream fout = new FileOutputStream(deviceInfoFile);
            fout.write(content);
            fout.flush();
            fout.close();
            return deviceInfoFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
