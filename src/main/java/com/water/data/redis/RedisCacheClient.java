package com.water.data.redis;


import com.google.common.base.Optional;


/**
 * 对CacheClient进行扩展，方法增强
 * Created by zhangmiaojie on 2018/1/12.
 */
public interface RedisCacheClient extends CacheClient<Optional> {
    void set(String key, Object obj);
}
