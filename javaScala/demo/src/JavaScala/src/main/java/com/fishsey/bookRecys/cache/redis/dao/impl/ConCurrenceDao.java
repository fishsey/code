package com.fishsey.bookRecys.cache.redis.dao.impl;

import com.fishsey.bookRecys.cache.redis.dao.AbstractBaseRedisDao;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created by root on 8/17/17.
 */
@Component
public class ConCurrenceDao extends AbstractBaseRedisDao<String, String>
{
    public void test_add(String key)
    {
        redisTemplate.execute(new RedisCallback<Integer>()
        {
            public Integer doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(key);
                long oldValue = Long.parseLong(serializer.deserialize(connection.get(keyByte)));
                oldValue += 1;
                byte[] newValueByte = serializer.serialize( oldValue + "");
                connection.set(keyByte, newValueByte);
                return null;
            }
        });
    }
}
