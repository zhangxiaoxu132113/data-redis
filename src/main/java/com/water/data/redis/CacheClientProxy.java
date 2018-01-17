package com.water.data.redis;

import com.google.common.base.Optional;

/**
 * Created by admin on 2018/1/12.
 */
public class CacheClientProxy implements RedisCacheClient {
    private CacheClientImpl cacheManager;

    public CacheClientProxy(String servers, String name) {
        this.cacheManager = new CacheClientImpl(servers, name);
    }

    @Override
    public Optional get(String key) {
        Optional optional = null;
        String str = cacheManager.get(key);
        if (str != null) {
            optional = Optional.fromNullable(str);
        }
        return optional;
    }

    @Override
    public Optional get(byte[] key) {
        return (Optional) cacheManager.get(key);
    }

    @Override
    public void set(byte[] key, byte[] bytes) {
        cacheManager.set(key, bytes);
    }

    @Override
    public void set(String key, Object obj) {
        Optional optional = Optional.fromNullable(obj);
        this.set(key.getBytes(), ProtobufUtil.ObjectToByte(optional));
    }
}
