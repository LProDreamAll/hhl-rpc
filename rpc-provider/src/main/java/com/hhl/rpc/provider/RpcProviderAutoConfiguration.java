package com.hhl.rpc.provider;

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
@EnableConfigurationProperties(RpcProperties.class)
@Slf4j
public class RpcProviderAutoConfiguration {
    @Bean
    public RpcProvider init(RpcProperties rpcProperties) throws Exception {
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistryType());
        RegistryService serviceRegistry = RegistryFactory.getInstance(rpcProperties.getRegistryAddr(), type);
        log.info("serviceRegistry hashcode :{}",serviceRegistry.hashCode());
        return new RpcProvider(rpcProperties.getServicePort(), serviceRegistry);
    }
}
