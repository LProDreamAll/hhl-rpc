package com.hhl.rpc.registry.loadbalancer;

import com.hhl.rpc.common.ServiceMeta;
import com.hhl.rpc.common.json.JSON;
import com.hhl.rpc.common.utils.MurmurHashUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  一致性哈希算法
 *   原理：
 *     虚拟，获取最近的值
 *  共享同一个
 *  1-应当监听和初始化Hash ring
 *  2-要加锁
 */
@Slf4j
public class ZKConsistentHashLoadBalancer implements ServiceLoadBalancer<ServiceInstance<ServiceMeta>>{
    @Override
    public ServiceInstance<ServiceMeta> select(List<ServiceInstance<ServiceMeta>> servers, int hashCode) {
        TreeMap<Integer, ServiceInstance<ServiceMeta>> ring = makeConsistentHashRing(servers);
        return allocateNode(ring, hashCode);
    }

    private ServiceInstance<ServiceMeta> allocateNode(TreeMap<Integer, ServiceInstance<ServiceMeta>> ring, int hashCode) {
        //获取不会小于 hashCode的key
        Map.Entry<Integer, ServiceInstance<ServiceMeta>> entry = ring.ceilingEntry(hashCode);
        if (null == entry){
            entry = ring.firstEntry();
        }
        return entry.getValue();
    }

    private TreeMap<Integer, ServiceInstance<ServiceMeta>> makeConsistentHashRing(List<ServiceInstance<ServiceMeta>> servers) {
        TreeMap<Integer, ServiceInstance<ServiceMeta>> ring = new TreeMap<>();
        for (ServiceInstance<ServiceMeta> instance : servers) {
            for (int i = 0; i < 10; i++) {
//                ring.put(MurmurHashUtils.hash((buildServiceInstanceKey(instance) + "#" + i)), instance);
                ring.put((buildServiceInstanceKey(instance) + "#" + i).hashCode(), instance);
            }
        }
        log.info("ring:{}", JSON.toJSONString(ring));
        return ring;
    }

    private String buildServiceInstanceKey(ServiceInstance<ServiceMeta> instance) {
        ServiceMeta payload = instance.getPayload();
        return String.join(":", payload.getServiceAddr(), String.valueOf(payload.getServicePort()));
    }
}
