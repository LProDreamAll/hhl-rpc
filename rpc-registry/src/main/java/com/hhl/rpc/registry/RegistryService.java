package com.hhl.rpc.registry;

import com.hhl.rpc.common.ServiceMeta;

import java.io.IOException;

public interface RegistryService {
    void register(ServiceMeta serviceMeta) throws Exception;

    void unRegister(ServiceMeta serviceMeta) throws Exception;

    ServiceMeta discovery(String serviceName, int invokerHashCode) throws Exception;

    void destroy() throws IOException;
}
