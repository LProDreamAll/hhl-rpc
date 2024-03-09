package com.hhl.rpc.codec;

import com.hhl.rpc.common.HhlRpcRequest;
import com.hhl.rpc.common.HhlRpcResponse;
import com.hhl.rpc.protocol.HhlRpcProtocol;
import com.hhl.rpc.protocol.MsgHeader;
import com.hhl.rpc.protocol.ProtocolConstants;
import com.hhl.rpc.serialization.RpcSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import com.hhl.rpc.protocol.MsgType;
import java.util.List;
import com.hhl.rpc.serialization.SerializationFactory;
public class HhlRpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
            return;
        }
        byteBuf.markReaderIndex();

        short magic = byteBuf.readShort();
        if (magic != ProtocolConstants.MAGIC) {
            throw new IllegalArgumentException("magic number is illegal, " + magic);
        }
        byte version = byteBuf.readByte();
        byte serializeType = byteBuf.readByte();
        byte msgType = byteBuf.readByte();
        byte status = byteBuf.readByte();
        long requestId = byteBuf.readLong();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        MsgType msgTypeEnum = MsgType.findByType(msgType);
        if (msgTypeEnum == null) {
            return;
        }
        MsgHeader header = new MsgHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setSerialization(serializeType);
        header.setStatus(status);
        header.setRequestId(requestId);
        header.setMsgType(msgType);
        header.setMsgLen(dataLength);
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(serializeType);
        switch (msgTypeEnum) {
            case REQUEST:
                HhlRpcRequest request = rpcSerialization.deserialize(data, HhlRpcRequest.class);
                if (request != null) {
                    HhlRpcProtocol<HhlRpcRequest> protocol = new HhlRpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(request);
                    list.add(protocol);
                }

            case RESPONSE:
                HhlRpcResponse response = rpcSerialization.deserialize(data, HhlRpcResponse.class);
                if (response != null) {
                    HhlRpcProtocol<HhlRpcResponse> protocol = new HhlRpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(response);
                    list.add(protocol);
                }
            case HEARTBEAT:
                // TODO
                break;

        }
    }
}
