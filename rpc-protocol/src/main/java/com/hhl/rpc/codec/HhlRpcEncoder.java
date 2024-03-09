package com.hhl.rpc.codec;

import com.hhl.rpc.protocol.HhlRpcProtocol;
import com.hhl.rpc.protocol.MsgHeader;
import com.hhl.rpc.serialization.RpcSerialization;
import com.hhl.rpc.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HhlRpcEncoder extends MessageToByteEncoder<HhlRpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HhlRpcProtocol<Object> protocol, ByteBuf byteBuf) throws Exception {
        MsgHeader header = protocol.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getVersion());
        byteBuf.writeByte(header.getSerialization());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());
        byte[] data = rpcSerialization.serialize(protocol.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
