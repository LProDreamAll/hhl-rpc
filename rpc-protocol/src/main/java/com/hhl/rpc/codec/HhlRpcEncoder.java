package com.hhl.rpc.codec;

import com.hhl.rpc.protocol.HhlRpcProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HhlRpcEncoder extends MessageToByteEncoder<HhlRpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HhlRpcProtocol<Object> objectHhlRpcProtocol, ByteBuf byteBuf) throws Exception {

    }
}
