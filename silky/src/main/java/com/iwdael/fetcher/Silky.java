package com.iwdael.fetcher;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public class Silky {
    private static volatile Silky sSilky;
    private final Config config;
    private final ThreadPoolExecutor executor;

    private Silky(Config config) {
        this.config = config;
        this.executor = new ThreadPoolExecutor(config.corePoolSize, config.maximumPoolSize, config.keepAliveTime, config.unit, config.workQueue);
    }

    private static Silky acquire() {
        if (sSilky == null) {
            synchronized (Silky.class) {
                if (sSilky == null) {
                    throw new RuntimeException(new IllegalAccessException("please init silky"));
                }
            }
        }
        return sSilky;
    }

    public static Silky chainFetcher(Chain<Chain.Fetch<?, ?>> chain) {
        acquire().config.fetches.add(chain);
        return acquire();
    }

    public static Silky chainInjector(Chain<Chain.Transform<?, ?>> chain) {
        acquire().config.transforms.add(chain);
        return acquire();
    }

    protected static void into(Task task) {
        TaskRunnable.execute(acquire().executor, task);
    }

    public static Task source(Object o) {
        return new Task(acquire().config, o);
    }

    public static Silky init(Config config) {
        if (sSilky == null) {
            synchronized (Silky.class) {
                if (sSilky == null) {
                    sSilky = new Silky(config);
                }
            }
        }
        return sSilky;
    }

    public static Config.Builder newConfigBuilder() {
        return new Config.Builder();
    }
}
