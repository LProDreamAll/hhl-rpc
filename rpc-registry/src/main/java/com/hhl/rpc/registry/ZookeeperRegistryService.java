package com.hhl.rpc.registry;

import com.hhl.rpc.common.ServiceMeta;

import java.io.IOException;

public class ZookeeperRegistryService implements RegistryService {
    public ZookeeperRegistryService(String registryAddr) {
    }

    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {
        
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode) throws Exception {
        return null;
    }

    @Override
    public void destroy() throws IOException {

    }
}
