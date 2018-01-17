package com.water.data.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

import java.util.ArrayList;

/**
 * Created by admin on 2018/1/12.
 */
public class CacheClientImpl implements CacheClient {
    private ShardedJedisPool jedisPool;
    private static final int DEFAULT_CO_TIMEOUT = 2000;
    private static final int DEFAULT_SO_TIMEOUT = 4000;
    private static final int MAX_TOTAL = 150;
    private static final int MAX_IDLE = 8;
    private static final int MAX_WAIT = 100;

    public CacheClientImpl(String servers, String name) {
        String[] hosts = servers.trim().split("\\|");
        ArrayList shards = new ArrayList();
        for(int i = 0; i < hosts.length; ++i) {
            String config = hosts[i];
            String[] ss = config.split(":");
            JedisShardInfo shard = new JedisShardInfo(ss[0], Integer.parseInt(ss[1]), DEFAULT_CO_TIMEOUT, DEFAULT_SO_TIMEOUT, 1);
            shards.add(shard);
        }

        GenericObjectPoolConfig var11 = new GenericObjectPoolConfig();
        var11.setMaxTotal(MAX_TOTAL);
        var11.setMaxIdle(MAX_IDLE);
        var11.setMaxWaitMillis(MAX_WAIT);
        var11.setTestWhileIdle(true);
        var11.setTimeBetweenEvictionRunsMillis(10000L);
        this.jedisPool = new ShardedJedisPool(var11, shards, Hashing.MURMUR_HASH);
    }

    @Override
    public String get(String key) {
        ShardedJedis redis = this.jedisPool.getResource();
        try {
            Jedis j = redis.getShard(key);
            String result = j.get(key);
            this.jedisPool.returnResource(redis);
            return result;
        } catch (Exception var6) {
            this.jedisPool.returnBrokenResource(redis);
            return null;
        }
    }

    @Override
    public Object get(byte[] key) {
        ShardedJedis redis = this.jedisPool.getResource();
        try {
            Jedis j = (Jedis)redis.getShard(key);
            byte[] result = j.get(key);
            this.jedisPool.returnResource(redis);
            return ProtobufUtil.ByteToObject(result);
        } catch (Exception var6) {
            this.jedisPool.returnBrokenResource(redis);
            return null;
        }
    }

    @Override
    public void set(byte[] key, byte[] bytes) {
        ShardedJedis redis = this.jedisPool.getResource();
        try {
            redis.set(key, bytes);
            this.jedisPool.returnResource(redis);
        } catch (Exception var6) {
            this.jedisPool.returnBrokenResource(redis);
        }
    }
}
