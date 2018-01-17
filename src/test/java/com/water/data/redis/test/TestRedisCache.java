package com.water.data.redis.test;

import com.google.common.base.Optional;
import com.water.data.redis.CacheClientProxy;
import com.water.data.redis.CacheManager;
import com.water.data.redis.RedisCacheClient;
import com.water.data.redis.entry.UserInfo;

/**
 * Created by zhangmiaojie on 2018/1/12.
 */
public class TestRedisCache {

    public static void main(String[] args) {
        RedisCacheClient cacheClient = CacheManager.getClient("127.0.0.1:6379", "");
        int userId = (int) (Math.random() * 10000);
        String key = "user:" + userId;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setUsername("zhangmiaojie");
        userInfo.setPassword("123456");

        cacheClient.set(key, userInfo);
        Optional<UserInfo> optional = cacheClient.get(key.getBytes());
        if (optional == null) {
            throw new RuntimeException(key + "不存在!请查询数据库!");
        }
        if (!optional.isPresent()) {
            throw new RuntimeException("数据库没有该" + key + "对应的数据");
        }
        UserInfo userInfo1 = optional.get();
        System.out.println(userInfo1.getId());
    }
}
