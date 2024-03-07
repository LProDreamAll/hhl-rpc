package com.hhl.rpc.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceMeta {

    private String serviceName;

    private String serviceVersion;

    private String serviceAddr;

    private int servicePort;

}
