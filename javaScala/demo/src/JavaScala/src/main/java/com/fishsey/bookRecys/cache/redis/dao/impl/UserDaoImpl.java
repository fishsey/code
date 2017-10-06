package com.fishsey.bookRecys.cache.redis.dao.impl;

import com.fishsey.bookRecys.cache.redis.dao.AbstractBaseRedisDao;
import com.fishsey.bookRecys.cache.redis.entity.User;
import org.junit.Assert;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by root on 8/6/17.
 */
@Component
public class UserDaoImpl extends AbstractBaseRedisDao<String, User>
{
    public boolean addIfNotExist(final User user)
    {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>()
        {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(user.getId());
                byte[] name = serializer.serialize(user.getName());
                return connection.setNX(key, name);
            }
        });
        return result;
    }

    public boolean addIfNotExist(final List<User> list)
    {
        Assert.assertNotNull(list);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                for (User user : list)
                {
                    byte[] key = serializer.serialize(user.getId());
                    byte[] name = serializer.serialize(user.getName());
                    connection.setNX(key, name);
                }
                return true;
            }
        }, false, true);

        return result;
    }

    public void delete(String key)
    {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keysByte = serializer.serialize(key);
                return connection.del(keysByte);
            }
        }, false, true);
    }

    public void delete(List<String> keys)
    {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException
            {
                byte[][] keysByte = new byte[keys.size()][];
                RedisSerializer<String> serializer = getRedisSerializer();
                int i = 0;
                for (String key : keys)
                {
                    keysByte[i++] = serializer.serialize(key);
                }
                return connection.del(keysByte);
            }
        }, false, true);
    }


    public boolean update(final User user)
    {
        String key = user.getId();
        if (get(key) == null)
        {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>()
        {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(user.getId());
                byte[] name = serializer.serialize(user.getName());
                connection.set(key, name);
                return true;
            }
        });
        return result;
    }

    public User get(final String keyId)
    {
        User result = redisTemplate.execute(new RedisCallback<User>()
        {
            public User doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] value = connection.get(key);
                if (value == null)
                {
                    return null;
                }
                String name = serializer.deserialize(value);
                return new User(keyId, name, null);
            }
        });
        return result;
    }
}  
