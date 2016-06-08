package com.tshevchuk.prayer.presentation;

/**
 * Created by taras on 05.04.16.
 */
public interface AsyncTaskManager {
    <T> void executeTask(BackgroundTask<T> backgroundTask);

    void cancelAll();

    interface BackgroundTask<T> {
        T doInBackground() throws Exception;

        void postExecute(T result);
        void onError(Throwable tr);
    }
}
