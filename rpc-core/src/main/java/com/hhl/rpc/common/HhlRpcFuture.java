package com.hhl.rpc.common;

import io.netty.util.concurrent.Promise;
import lombok.Data;

@Data
public class HhlRpcFuture<T> {
    private Promise<T> promise;
    private long timeout;

    public HhlRpcFuture(Promise<T> promise, long timeout) {
        this.promise = promise;
        this.timeout = timeout;
    }
}
