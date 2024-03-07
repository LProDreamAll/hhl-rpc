package com.hhl.rpc.provider.config;

import com.hhl.rpc.common.RpcProperties;
import com.hhl.rpc.provider.RpcProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcProviderAutoConfiguration {

    //暴露RpcProvider bean
    @Bean
    public RpcProvider init(RpcProperties rpcProperties) throws Exception {
        return new RpcProvider(rpcProperties);
    }
}
