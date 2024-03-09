package com.hhl.rpc.consumer.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 构造出一个可以真正进行 RPC 调用的 Bean，然后将它注册到 Spring 的容器中
 * 1-需要直接从配置中心读取class元数据,并生成相关的类
 * 2-该 Bean 执行的所有方法进行拦截。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Autowired
public @interface RpcReference {
    String serviceVersion() default "1.0";

    String registryType() default "ZOOKEEPER";

    String registryAddress() default "127.0.0.1:2181";

    long timeout() default 5000;

}
