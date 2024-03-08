package com.hhl.rpc.common;

import io.netty.util.concurrent.Promise;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HhlRpcFuture<T> {
    private Promise<T> promise;
    private long timeout;
}
