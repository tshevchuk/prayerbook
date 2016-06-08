package com.tshevchuk.prayer.presentation;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

/**
 * Created by taras on 05.04.16.
 */
public class AsyncTaskManagerImpl implements AsyncTaskManager {
    private static final String TAG = AsyncTaskManagerImpl.class.getName();
    private ArrayList<AsyncTask<Void, Void, Object>> tasks = new ArrayList<>();

    @DebugLog
    @Override
    public <T> void executeTask(final BackgroundTask<T> backgroundTask) {
        AsyncTask<Void, Void, Object> task = new AsyncTask<Void, Void, Object>() {
            @DebugLog
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    return backgroundTask.doInBackground();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground error", e);
                    return e;
                }
            }

            @DebugLog
            @Override
            protected void onPostExecute(Object res) {
                tasks.remove(this);

                if (res instanceof Throwable) {
                    backgroundTask.onError((Throwable) res);
                } else {
                    //noinspection unchecked
                    backgroundTask.postExecute((T) res);
                }
            }
        };
        tasks.add(task);
        task.execute();
    }

    @DebugLog
    @Override
    public void cancelAll() {
        for (int i = tasks.size() - 1; i >= 0; --i) {
            tasks.get(i).cancel(false);
        }
        tasks.clear();
    }
}
