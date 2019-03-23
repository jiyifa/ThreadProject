package com.jiyifa.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 工具类
 */
@Component
public class RedisUtils {
    @Autowired
    private JedisPool jedisPool;

    public Long getIncr(String key,int timeout){
        Jedis redis = null;
        try {
            redis = jedisPool.getResource();
            /**
             * incr(key)是redis的一个同步方法，用于对key自增加，当key不存在时，则创建为0的key
             */
            long id = redis.incr(key);
            if(timeout > 0){
                //设置超时，很重要！很重要！很重要！
                redis.expire(key,timeout);
            }
            return id;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(redis != null){
                redis.close();
            }
        }
       return null;
    }
}
