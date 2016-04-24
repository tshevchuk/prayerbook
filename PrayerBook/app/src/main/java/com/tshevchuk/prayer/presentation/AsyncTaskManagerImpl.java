package com.tshevchuk.prayer.presentation;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by taras on 05.04.16.
 */
public class AsyncTaskManagerImpl implements AsyncTaskManager {
    private static final String TAG = AsyncTaskManagerImpl.class.getName();
    private ArrayList<AsyncTask<Void, Void, Object>> tasks = new ArrayList<>();

    @Override
    public <T> void executeTask(BackgroundTask<T> backgroundTask) {
        final WeakReference<BackgroundTask<T>> backgroundTaskRef = new WeakReference<>(backgroundTask);

        AsyncTask<Void, Void, Object> task = new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                BackgroundTask<T> task = backgroundTaskRef.get();
                if (task == null) {
                    return null;
                }
                try {
                    return task.doInBackground();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground error", e);
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Object res) {
                tasks.remove(this);

                BackgroundTask<T> task = backgroundTaskRef.get();
                if (task == null) {
                    return;
                }

                if (res instanceof Throwable) {
                    task.onError((Throwable) res);
                } else {
                    //noinspection unchecked
                    task.postExecute((T) res);
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
