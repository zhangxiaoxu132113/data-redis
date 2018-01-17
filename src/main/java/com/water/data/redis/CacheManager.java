package com.water.data.redis;

/**
 * Created by zhangmiaojie on 2018/1/12.
 */
public class CacheManager {
    private CacheManager() {
    }

    public static RedisCacheClient getClient(String servers, String name) {
        return new CacheClientProxy(servers, name);
    }
}
