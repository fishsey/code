package com.fishsey.bookRecys.cache.redis.dao.impl;

import com.fishsey.bookRecys.cache.redis.dao.AbstractBaseRedisDao;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by root on 8/12/17.
 */
@Component
public class InverseIndexDao extends AbstractBaseRedisDao<Integer, Integer>
{
    public boolean add(int key, int member, double tfidf)
    {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>()
        {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(key + "");
                byte[] memberByte = serializer.serialize(member + "");

                return connection.zAdd(keyByte, tfidf, memberByte);
            }
        });
        return result;
    }


    public ArrayList<Integer> getRange(int key, long start, long end)
    {
        ArrayList<Integer>  result = redisTemplate.execute(new RedisCallback< ArrayList<Integer> >()
        {
            public ArrayList<Integer> doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();

                byte[] keyByte = serializer.serialize(key + "");

                Set<byte[]> result = connection.zRange(keyByte, start, end);
                ArrayList<Integer> retResult = new ArrayList();

                for (byte[] bytes : result)
                {
                    retResult.add(Integer.parseInt(serializer.deserialize(bytes)));
                }
                return retResult;
            }
        });
        return result;
    }

}
