package com.hhl.rpc.handler;


import com.hhl.rpc.common.utils.ThreadUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class RpcRequestProcessor {
    public static void submitRequest(Runnable task) {
        CompletableFuture.runAsync(task, ThreadUtils.createWorkPool("RpcRequestProcessor",
                10,10));
    }
}
