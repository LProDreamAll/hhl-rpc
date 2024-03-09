package com.hhl.rpc.common.utils;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;


public class MurmurHashUtils {

    /**
     * MurMurHash算法, 性能高, 碰撞率低
     *
     * @param str String
     * @return Long
     */
    public static int hash(String str) {
        HashFunction hashFunction = Hashing.murmur3_128();
        return hashFunction.hashString(str, StandardCharsets.UTF_8).asInt();
    }


    /**
     * Long转换成无符号长整型（C中数据类型）
     * Java的数据类型long与C语言中无符号长整型uint64_t有区别，导致Java输出版本存在负数
     *
     * @param value long
     * @return Long
     */
    public static Long readUnsignedLong(long value) {
        if (value >= 0) {
            return value;
        }
        return value & Long.MAX_VALUE;
    }

    /**
     * 返回无符号murmur hash值
     *
     * @param key
     * @return
     */
    public static Long hashUnsigned(String key) {
        return readUnsignedLong(hash(key));
    }

}
