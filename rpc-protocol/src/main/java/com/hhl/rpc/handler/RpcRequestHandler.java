package com.hhl.rpc.handler;

import com.hhl.rpc.common.HhlRpcRequest;
import com.hhl.rpc.protocol.HhlRpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<HhlRpcProtocol<HhlRpcRequest>> {

    private final Map<String, Object> rpcServiceMap;

    public RpcRequestHandler(Map<String, Object> rpcServiceMap) {
        this.rpcServiceMap = rpcServiceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HhlRpcProtocol<HhlRpcRequest> hhlRpcRequestHhlRequestHandler) throws Exception {

    }
}
