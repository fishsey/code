package com.fishsey.bookRecys.cache.redis.dao.impl;

import com.fishsey.bookRecys.cache.redis.dao.AbstractBaseRedisDao;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created by root on 8/6/17.
 */
@Component
public class BaseDaoImpl extends AbstractBaseRedisDao<String, String>
{
    public boolean addIfNotExist(String key, String value)
    {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>()
        {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(key);
                byte[] valueByte = serializer.serialize(value);
                return connection.setNX(keyByte, valueByte);
            }
        });
        return result;
    }
}  
