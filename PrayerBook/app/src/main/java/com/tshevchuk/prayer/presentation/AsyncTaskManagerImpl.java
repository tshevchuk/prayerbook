package com.tshevchuk.prayer.presentation;

import android.os.AsyncTask;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by taras on 05.04.16.
 */
public class AsyncTaskManagerImpl implements AsyncTaskManager {
    private static final String TAG = AsyncTaskManagerImpl.class.getName();
    private final ArrayList<AsyncTask<Void, Void, Object>> tasks = new ArrayList<>();

    @Override
    public <T> void executeTask(final BackgroundTask<T> backgroundTask) {
        AsyncTask<Void, Void, Object> task = new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    return backgroundTask.doInBackground();
                } catch (Exception e) {
                    Timber.e(e, "doInBackground error");
                    return e;
                }
            }

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

    @Override
    public void cancelAll() {
        for (int i = tasks.size() - 1; i >= 0; --i) {
            tasks.get(i).cancel(false);
        }
        tasks.clear();
    }
}
