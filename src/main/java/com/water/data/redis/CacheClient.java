package com.water.data.redis;


/**
 * Created by admin on 2018/1/12.
 */
public interface CacheClient<T> {
    T get(String key);

    T get(byte[] key);

    void set(byte[] key, byte[] bytes);
}
