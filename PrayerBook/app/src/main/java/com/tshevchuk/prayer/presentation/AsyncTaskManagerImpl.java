package com.tshevchuk.prayer.presentation;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by taras on 05.04.16.
 */
public class AsyncTaskManagerImpl implements AsyncTaskManager {
    private ArrayList<AsyncTask<Void, Void, Object>> tasks = new ArrayList<>();

    @Override
    public <T> void executeTask(final Callable<T> backgroundTask, PostExecuteTask<T> postExecuteTask) {
        final WeakReference<PostExecuteTask<T>> postExecuteTaskRef = new WeakReference<>(postExecuteTask);

        AsyncTask<Void, Void, Object> task = new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    return backgroundTask.call();
                } catch (Exception e) {
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Object res) {
                tasks.remove(this);

                PostExecuteTask<T> task = postExecuteTaskRef.get();
                if (task == null) {
                    return;
                }

                if (res instanceof Throwable) {
                    task.onError((Throwable) res);
                } else {
                    //noinspection unchecked
                    task.call((T) res);
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
