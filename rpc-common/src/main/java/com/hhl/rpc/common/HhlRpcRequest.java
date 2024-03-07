package com.hhl.rpc.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class HhlRpcRequest implements Serializable {
    private String serviceVersion;
    private String className;
    private String methodName;
    private Object[] params;
    private Class<?>[] parameterTypes;
}
