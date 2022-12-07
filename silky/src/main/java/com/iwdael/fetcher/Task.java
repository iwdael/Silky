package com.iwdael.fetcher;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public class Task {
    protected final Object source;
    protected final Config config;
    protected Object prepared;
    protected Object loading;
    protected Object error;
    protected Object target;
    protected long priority;

    public Task(Config config, Object o) {
        this.config = config;
        this.source = o;
    }

    public Task prepared(Object prepared) {
        this.prepared = prepared;
        return this;
    }

    public Task loading(Object loading) {
        this.loading = loading;
        return this;
    }

    public Task error(Object error) {
        this.error = error;
        return this;
    }

    public Task target(Object target) {
        this.target = target;
        return this;
    }

    public Task priority(long priority) {
        this.priority = priority;
        return this;
    }

    public void into(Object target) {
        this.target = target;
        Silky.into(this);
    }
}
