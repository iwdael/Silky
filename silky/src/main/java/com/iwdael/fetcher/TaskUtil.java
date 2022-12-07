package com.iwdael.fetcher;

import android.os.Handler;
import android.os.Looper;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Silky
 */
public class TaskUtil {
    private static volatile Handler handler = null;

    public static synchronized Handler checkAndInit(){
        if (handler==null) handler = new Handler(Looper.getMainLooper());
        return handler;
    }

    public static synchronized void prepared(Task task) {
        if (task.prepared == null) return;
        for (Chain<Chain.Transform<?, ?>> chain : task.config.transforms) {
            if (chain.source == task.prepared.getClass() && chain.target.isAssignableFrom(task.target.getClass())) {
                Object next = chain.chain;
                Object prepared = task.prepared;
                while (next != null) {
                    if (next instanceof Chain.Transform) {
                        Chain.Transform<Object, Object> transform = (Chain.Transform<Object, Object>) next;
                        prepared = transform.factory.create().transform(prepared);
                        next = transform.next;
                    } else {
                        Chain.Inject<Object, Object> inject = (Chain.Inject<Object, Object>) next;
                        Object finalPrepared = prepared;
                        checkAndInit().post(() -> inject.factory.create().inject(task.target, finalPrepared));
                        next = null;
                    }
                }
                break;
            }
        }
    }
}
