package com.iwdael.fetcher;

import static com.iwdael.fetcher.Silky.newConfigBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public class Config {
    public final int corePoolSize;
    public final int maximumPoolSize;
    public final long keepAliveTime;
    public final TimeUnit unit;
    public final BlockingQueue<Runnable> workQueue;
    public final List<Chain<Chain.Fetch<?, ?>>> fetches = new ArrayList<>();
    public final List<Chain<Chain.Transform<?, ?>>> transforms = new ArrayList<>();
    public static final Config defaultConfig = newConfigBuilder().build();
    public final List<Future<?>> executors = new ArrayList<>();

    public Config(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.workQueue = workQueue;
    }


    public static class Builder {
        private int corePoolSize = 0;
        private int maximumPoolSize = Integer.MAX_VALUE;
        private long keepAliveTime = 60;
        private TimeUnit unit = TimeUnit.SECONDS;
        private BlockingQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();

        protected Builder() {
        }

        public Config.Builder corePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }

        public Config.Builder maximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
            return this;
        }

        public Config.Builder keepAliveTime(long keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        public Config.Builder unit(TimeUnit unit) {
            this.unit = unit;
            return this;
        }

        public Config.Builder workQueue(BlockingQueue<Runnable> workQueue) {
            this.workQueue = workQueue;
            return this;
        }


        public Config build() {
            return new Config(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }
    }
}
