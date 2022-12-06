package com.iwdael.fetcher.example;

import android.widget.TextView;

import com.iwdael.fetcher.Silky;
import com.iwdael.fetcher.Fetcher;
import com.iwdael.fetcher.Injector;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public class Main {


    public static void main(String[] args) {
        Silky.newFetcher()
                .fetch(String.class, () -> (Fetcher<String, Short>) Short::parseShort)
                .fetch(() -> (Fetcher<Short, Integer>) Short::intValue)
                .build(Integer.class);
        Silky.newInjector()
                .transform(Integer.class, () -> String::valueOf)
                .transform(() -> Integer::parseInt)
                .inject((Injector.Factory<Integer, TextView>) () -> TextView::setText)
                .build(TextView.class);
    }
}
