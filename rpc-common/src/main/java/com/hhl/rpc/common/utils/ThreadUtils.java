package com.hhl.rpc.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author: lihanghang
 */
public class ThreadUtils {
    /**
     * Number of Threads = Number of Available CPU Cores * Target CPU Utilization * (1 + Wait Time / Compute Time)
     */

    private static final Map<String, ThreadPoolExecutor> innerCache = new HashMap<>();

    private final static int MAX_QUEUE_CAPACITY = 10000;

    private static int getCorePoolSize() {
        return Runtime.getRuntime().availableProcessors();
    }

    private static int getMaxPoolSize() {
        return (int) Math.round(Runtime.getRuntime().availableProcessors() * 0.8 * (1 + 10 / 1));
    }

    public static <U> CompletableFuture<U> doCommonAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, createWorkPool("Common"));
    }

    public static synchronized ThreadPoolExecutor createWorkPool(String threadNamePrefix) {
        if (!innerCache.containsKey(threadNamePrefix)) {
            innerCache.put(threadNamePrefix, new ThreadPoolExecutor(
                    getCorePoolSize(),
                    getMaxPoolSize(),
                    60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(MAX_QUEUE_CAPACITY), new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build()));
        }

        return innerCache.get(threadNamePrefix);
    }

    public static synchronized ThreadPoolExecutor createWorkPool(String threadNamePrefix, int corePoolSize, int axPoolSize) {
        if (!innerCache.containsKey(threadNamePrefix)) {
            innerCache.put(threadNamePrefix, new ThreadPoolExecutor(
                    getCorePoolSize(),
                    getMaxPoolSize(),
                    60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(MAX_QUEUE_CAPACITY), new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build()));
        }

        return innerCache.get(threadNamePrefix);
    }

}
