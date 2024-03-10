package com.hhl.rpc.consumer;

import com.hhl.rpc.codec.HhlRpcDecoder;
import com.hhl.rpc.codec.HhlRpcEncoder;
import com.hhl.rpc.common.HhlRpcRequest;
import com.hhl.rpc.common.RpcServiceHelper;
import com.hhl.rpc.common.ServiceMeta;
import com.hhl.rpc.handler.RpcResponseHandler;
import com.hhl.rpc.protocol.HhlRpcProtocol;
import com.hhl.rpc.registry.RegistryService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
public class RpcConsumer {
    private static Bootstrap bootstrap;
    private static EventLoopGroup eventLoopGroup;

    public static void sendRequest(HhlRpcProtocol<HhlRpcRequest> protocol, RegistryService registryService) throws Exception {
        HhlRpcRequest request = protocol.getBody();
        Object[] params = request.getParams();
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());

        int invokerHashCode = params.length > 0 ? params[0].hashCode() : serviceKey.hashCode();
        ServiceMeta serviceMetadata = registryService.discovery(serviceKey, invokerHashCode);

        if (serviceMetadata != null) {
            ChannelFuture future = bootstrap.connect(serviceMetadata.getServiceAddr(), serviceMetadata.getServicePort()).sync();
            future.addListener((ChannelFutureListener) arg0 -> {
                if (future.isSuccess()) {
                    log.info("connect rpc server {} on port {} success.", serviceMetadata.getServiceAddr(), serviceMetadata.getServicePort());
                } else {
                    log.error("connect rpc server {} on port {} failed.", serviceMetadata.getServiceAddr(), serviceMetadata.getServicePort());
                    future.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();
                }
            });
            future.channel().writeAndFlush(protocol);
        }
    }

    @PostConstruct
    public void init() {
        new Thread(() -> {
            try {
                log.info("startRpcConsumer");
                startRpcConsumer();
            } catch (Exception e) {
                log.error("start rpc server error.", e);
            }
        }).start();
    }

    private void startRpcConsumer() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(4);
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new HhlRpcEncoder())
                                .addLast(new HhlRpcDecoder())
                                .addLast(new RpcResponseHandler());
                    }
                });
    }
}
