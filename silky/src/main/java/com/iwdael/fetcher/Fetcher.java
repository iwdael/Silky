package com.iwdael.fetcher;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public interface Fetcher<S, T> {
    T fetch(S source);

    interface Factory<S, T> {
        Fetcher<S, T> create();
    }
}
