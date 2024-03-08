package com.hhl.rpc.handler;


import com.hhl.rpc.common.HhlRpcFuture;
import com.hhl.rpc.common.HhlRpcRequestHolder;
import com.hhl.rpc.common.HhlRpcResponse;
import com.hhl.rpc.protocol.HhlRpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcResponseHandler extends SimpleChannelInboundHandler<HhlRpcProtocol<HhlRpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HhlRpcProtocol<HhlRpcResponse> msg) {
        long requestId = msg.getHeader().getRequestId();
        HhlRpcFuture<HhlRpcResponse> future = HhlRpcRequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getBody());
    }
}

