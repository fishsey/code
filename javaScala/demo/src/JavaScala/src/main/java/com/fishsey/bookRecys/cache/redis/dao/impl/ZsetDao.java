package com.fishsey.bookRecys.cache.redis.dao.impl;

import com.fishsey.bookRecys.cache.redis.dao.AbstractBaseRedisDao;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created by root on 8/19/17.
 */
@Component
public class ZsetDao extends AbstractBaseRedisDao<String, String>
{
    //default score is 0
    public void test_add(String key)
    {
        redisTemplate.execute(new RedisCallback<Integer>()
        {
            public Integer doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] memberByte = serializer.serialize(key);
                byte[] keyByte = serializer.serialize("ch");
                connection.zAdd(keyByte, 0.0, memberByte);
                return null;
            }
        });
    }
}
