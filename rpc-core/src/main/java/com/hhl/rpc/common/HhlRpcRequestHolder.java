package com.hhl.rpc.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class HhlRpcRequestHolder {

    public final static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static final Map<Long, HhlRpcFuture<HhlRpcResponse>> REQUEST_MAP = new ConcurrentHashMap<>();
}
