package com.hhl.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class HhlRpcProtocol<T> implements Serializable {
    private MsgHeader header;
    private T body;
}
