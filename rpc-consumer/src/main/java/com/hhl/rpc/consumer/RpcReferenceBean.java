package com.hhl.rpc.consumer;

import com.hhl.rpc.registry.RegistryFactory;
import com.hhl.rpc.registry.RegistryService;
import com.hhl.rpc.registry.RegistryType;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;
@Setter
public class RpcReferenceBean implements FactoryBean<Object> {

    private Class<?> interfaceClass;

    private String serviceVersion;

    private String registryType;

    private String registryAddr;

    private long timeout;

    private Object object;

    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    /**
     * init 方法何时被调用的
     * @throws Exception
     */
    public void init() throws Exception {
        RegistryService registryService = RegistryFactory.getInstance(this.registryAddr, RegistryType.valueOf(this.registryType));
        this.object = Proxy.newProxyInstance(this.interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvokerProxy(serviceVersion, timeout, registryService));
    }
}
