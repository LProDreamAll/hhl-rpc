package com.hhl.rpc.consumer;

import com.hhl.rpc.common.HhlRpcFuture;
import com.hhl.rpc.common.HhlRpcRequest;
import com.hhl.rpc.common.HhlRpcRequestHolder;
import com.hhl.rpc.common.HhlRpcResponse;
import com.hhl.rpc.protocol.HhlRpcProtocol;
import com.hhl.rpc.protocol.MsgHeader;
import com.hhl.rpc.protocol.MsgType;
import com.hhl.rpc.protocol.ProtocolConstants;
import com.hhl.rpc.registry.RegistryService;
import com.hhl.rpc.serialization.SerializationTypeEnum;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private final String serviceVersion;
    private final long timeout;
    private final RegistryService registryService;

    public RpcInvokerProxy(String serviceVersion, long timeout, RegistryService registryService) {
        this.serviceVersion = serviceVersion;
        this.timeout = timeout;
        this.registryService = registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HhlRpcProtocol<HhlRpcRequest> protocol = new HhlRpcProtocol<>();
        MsgHeader header = new MsgHeader();
        long requestId = HhlRpcRequestHolder.REQUEST_ID_GEN.incrementAndGet();
        header.setMagic(ProtocolConstants.MAGIC);
        header.setVersion(ProtocolConstants.VERSION);
        header.setRequestId(requestId);
        header.setSerialization((byte) SerializationTypeEnum.HESSIAN.getType());
        header.setMsgType((byte) MsgType.REQUEST.getType());
        header.setStatus((byte) 0x1);
        protocol.setHeader(header);

        HhlRpcRequest request = new HhlRpcRequest();
        request.setServiceVersion(this.serviceVersion);
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        protocol.setBody(request);

//        RpcConsumer rpcConsumer = new RpcConsumer();
//        log.info("rpcConsumer hashcode :{}",rpcConsumer.hashCode());
        HhlRpcFuture<HhlRpcResponse> future = new HhlRpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()), timeout);
        HhlRpcRequestHolder.REQUEST_MAP.put(requestId, future);
        RpcConsumer.sendRequest(protocol, this.registryService);

        // TODO hold request by ThreadLocal


        return future.getPromise().get(future.getTimeout(), TimeUnit.MILLISECONDS).getData();
    }
}
