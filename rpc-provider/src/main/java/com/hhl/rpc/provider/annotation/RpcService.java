package com.hhl.rpc.provider.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @RpcService(serviceInterface = HelloFacade.class, serviceVersion = "1.0.0")
 * public class HelloFacadeImpl implements HelloFacade {
 * @Override public String hello(String name) {
 * return "hello" + name;
 * }
 * }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//需要继承spring创建Bean的功能
@Component
public @interface RpcService {
    String serviceVersion() default "1.0.0";

    Class<?> serviceInterface() default Object.class;

}
