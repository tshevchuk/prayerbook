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
public class ScreenshotFileManager {
    private static final String TAG = ScreenshotFileManager.class.getName();
    private final Context context;


    public ScreenshotFileManager(Context context) {
        this.context = context;
    }

    public File storeErrorReportScreenshot(byte[] screenshot) {
        File dir = new File(context.getCacheDir(), "error_report_screenshots");
        if (!dir.exists() && !dir.mkdirs()) {
            Log.d(TAG, "Can't create directory");
            return null;
        }

        File imageFile = new File(dir,
                "prayerbook_error_image_" + System.currentTimeMillis() + ".png");

        try {
            OutputStream fout = new FileOutputStream(imageFile);
            fout.write(screenshot);
            fout.flush();
            fout.close();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
