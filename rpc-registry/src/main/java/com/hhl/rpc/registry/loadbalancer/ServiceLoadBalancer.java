package com.hhl.rpc.registry.loadbalancer;

import java.util.List;
import java.util.Map;

public interface ServiceLoadBalancer<T> {
    T select(List<T> servers, int hashCode);

    //todo 初始化 LoadBalancer 对象

//    /**
//     * 可能是hash环，list等
//     * @param servers
//     * @return
//     */
//    Object init(List<T> servers);
//    void reLoad(List<T> servers);

}
