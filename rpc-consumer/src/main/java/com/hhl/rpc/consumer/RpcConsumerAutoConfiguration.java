package com.hhl.rpc.consumer;

import com.hhl.rpc.common.RpcProperties;
import com.hhl.rpc.registry.RegistryFactory;
import com.hhl.rpc.registry.RegistryService;
import com.hhl.rpc.registry.RegistryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@Slf4j
public class RpcConsumerAutoConfiguration {

    @Bean
    public RpcConsumer init() throws Exception {
        return new RpcConsumer();
    }
}
