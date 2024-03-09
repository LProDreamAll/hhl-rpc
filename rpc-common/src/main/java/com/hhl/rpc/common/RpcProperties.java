package com.hhl.rpc.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {

    private int servicePort;

    private String serviceHost;

    private int registryPort;

    private String registryHost;

    private String registryAddr;

    private String registryType;

    public String getRegistryAddr() {
        return String.join(":", this.registryHost, this.registryPort + "");
    }
}
