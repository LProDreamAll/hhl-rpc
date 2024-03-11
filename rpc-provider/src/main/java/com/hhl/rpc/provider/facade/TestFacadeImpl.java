package com.hhl.rpc.provider.facade;

import com.hhl.rpc.provider.annotation.RpcService;

@RpcService(serviceInterface = TestFacade.class, serviceVersion = "1.0.0")
public class TestFacadeImpl implements TestFacade {

    @Override
    public String test(String name) {
        return "TestFacade " + name;
    }
}
