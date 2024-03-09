package com.hhl.rpc.provider.service;

import com.hhl.rpc.facade.HelloFacade;
import com.hhl.rpc.provider.annotation.RpcService;

@RpcService(serviceVersion = "1.0.0", serviceInterface = HelloFacade.class)
public class HelloFacadeImpl implements HelloFacade {
    @Override
    public String hello(String name) {
        return "hello" + name;
    }

    @Override
    public String hello1(String name) {
        return "hello1" + name;
    }
}
