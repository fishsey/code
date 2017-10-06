package com.fishsey.bookRecys.cache.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Created by root on 8/6/17.
 */
public abstract class AbstractBaseRedisDao<K, V>
{
    @Autowired
    protected RedisTemplate<K, V> redisTemplate;

    protected RedisSerializer<String> getRedisSerializer()
    {
        return redisTemplate.getStringSerializer();
    }
}  
