package com.hhl.rpc.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class HhlRpcResponse implements Serializable {
    private Object data;
    private String message;
}
