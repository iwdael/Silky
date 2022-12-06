package com.iwdael.fetcher;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public interface Injector<S, T> {
    void inject(T target, S source);

    interface Factory<S, T> {
        Injector<S, T> create();
    }
}
