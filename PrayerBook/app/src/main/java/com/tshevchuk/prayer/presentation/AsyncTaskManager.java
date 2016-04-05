package com.tshevchuk.prayer.presentation;

import java.util.concurrent.Callable;

/**
 * Created by taras on 05.04.16.
 */
public interface AsyncTaskManager {
    <T> void executeTask(Callable<T> backgroundTask, PostExecuteTask<T> postExecuteTask);

    void cancelAll();

    interface PostExecuteTask<T> {
        void call(T param);

        void onError(Throwable tr);
    }
}
