package com.hhl.rpc.consumer.controller;

import com.google.common.base.Stopwatch;
import com.hhl.rpc.consumer.annotation.RpcReference;
import com.hhl.rpc.provider.facade.HelloFacade;
import com.hhl.rpc.provider.facade.TestFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class HelloController {

    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "SpringJavaInjectionPointsAutowiringInspection"})
    @RpcReference(serviceVersion = "1.0.0", timeout = 3000)
    private HelloFacade helloFacade;


    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "SpringJavaInjectionPointsAutowiringInspection"})
    @RpcReference(serviceVersion = "1.0.0", timeout = 3000)
    private TestFacade testFacade;
    @RequestMapping(value = "/test/facade", method = RequestMethod.GET)
    public String sayTestFacade() {
        Stopwatch started = Stopwatch.createStarted();
        String test = testFacade.test(" sayTestFacade ");
        log.info("test time :{}",started.elapsed(TimeUnit.MILLISECONDS));
        return test;
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return helloFacade.hello("hhl rpc");
    }
}
