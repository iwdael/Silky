package com.iwdael.fetcher;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public class TaskRunnable implements Runnable {
    private final Task task;

    public TaskRunnable(Task task) {
        this.task = task;
    }

    public static synchronized void execute(ThreadPoolExecutor executor, Task task) {
        TaskUtil.prepared(task);
        Future<?> future = executor.submit(new TaskRunnable(task));
        task.config.executors.add(future);
    }

    @Override
    public void run() {

    }
}
