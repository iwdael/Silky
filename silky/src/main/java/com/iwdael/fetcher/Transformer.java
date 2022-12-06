package com.iwdael.fetcher;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public interface Transformer<S, T> {
    T transform(S source);

    interface Factory<S, T> {
        Transformer<S, T> create();
    }
}
