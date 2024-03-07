package com.hhl.rpc.provider;

import com.hhl.rpc.codec.HhlRpcDecoder;
import com.hhl.rpc.codec.HhlRpcEncoder;
import com.hhl.rpc.common.RpcProperties;
import com.hhl.rpc.common.RpcServiceHelper;
import com.hhl.rpc.common.ServiceMeta;
import com.hhl.rpc.handler.HhlRequestHandler;
import com.hhl.rpc.provider.annotation.RpcService;
import com.hhl.rpc.registry.RegistryFactory;
import com.hhl.rpc.registry.RegistryService;
import com.hhl.rpc.registry.RegistryType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 接口和接口版本和spring的bean serviceImpl进行绑定
 * 1-启动rpc服务，暴露端口
 * 2-扫描所有需要暴露的服务接口，元数据信息发布到注册信息
 */
@Slf4j
public class RpcProvider implements BeanPostProcessor {

    private final RpcProperties rpcProperties;
    private final RegistryService serviceRegistry;

    private final Map<String, Object> rpcServiceMap = new HashMap<>();

    public RpcProvider(RpcProperties rpcProperties) throws Exception {
        this.rpcProperties = rpcProperties;
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistryType());
        this.serviceRegistry = RegistryFactory.getInstance(rpcProperties.getRegistryAddr(), type);
    }


    @PostConstruct
    private void init() {
        new Thread(() -> {
            try {
                startRpcServer();
            } catch (Exception e) {
                log.error("start rpc server error.", e);
            }
        }).start();
    }

    private void startRpcServer() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new HhlRpcEncoder())
                                    .addLast(new HhlRpcDecoder())
                                    .addLast(new HhlRequestHandler(rpcServiceMap));
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(rpcProperties.getRegistryAddr(), rpcProperties.getServicePort()).sync();
            log.info("server addr {} started on port {}", rpcProperties.getRegistryAddr(), rpcProperties.getServicePort());
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (Objects.nonNull(rpcService)) {
            ServiceMeta serviceMeta = ServiceMeta.builder().serviceAddr(rpcProperties.getRegistryAddr())
                    .servicePort(rpcProperties.getServicePort()).serviceName(rpcService.serviceInterface().getName())
                    .serviceVersion(rpcService.serviceVersion())
                    .build();
            try {
                serviceRegistry.register(serviceMeta);
                rpcServiceMap.put(RpcServiceHelper.buildServiceKey(rpcService.serviceInterface().getName(),rpcService.serviceVersion()),bean);
            } catch (Exception e) {
                log.error("failed to register service {}#{}", rpcService.serviceInterface().getName(), rpcService.serviceVersion(), e);
            }
        }
        return bean;
    }
}
