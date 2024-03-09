package com.hhl.rpc.provider.config;

import com.hhl.rpc.common.RpcProperties;
import com.hhl.rpc.provider.RpcProvider;
import com.hhl.rpc.registry.RegistryFactory;
import com.hhl.rpc.registry.RegistryService;
import com.hhl.rpc.registry.RegistryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(RpcProperties.class)
@Slf4j
public class RpcProviderAutoConfiguration {

    //暴露RpcProvider bean
    @Bean
    public RpcProvider initRpcProvider(RpcProperties rpcProperties) throws Exception {
        RegistryService serviceRegistry = RegistryFactory.getInstance(rpcProperties.getRegistryAddr(),
                RegistryType.valueOf(rpcProperties.getRegistryType()));
        log.info("serviceRegistry hashcode :{}", serviceRegistry.hashCode());
        return new RpcProvider(rpcProperties, serviceRegistry);
    }
}
