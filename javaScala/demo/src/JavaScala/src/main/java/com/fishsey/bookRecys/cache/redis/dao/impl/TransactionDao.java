package com.fishsey.bookRecys.cache.redis.dao.impl;

import com.fishsey.bookRecys.cache.redis.dao.AbstractBaseRedisDao;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 8/17/17.
 */
@Component
public class TransactionDao extends AbstractBaseRedisDao<String, String>
{
    public void test_pipeline(String key)
    {
        List<Long> res = redisTemplate.execute(new RedisCallback<List<Long>>()
        {
            public List<Long> doInRedis(RedisConnection connection) throws DataAccessException
            {
                try
                {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);

                    //begin a tx
                    //connection.multi();
                    connection.incr(keyByte);
                    System.out.println(Thread.currentThread().toString()+ " " + System.currentTimeMillis());

                    Thread.currentThread().interrupt();
                    TimeUnit.SECONDS.sleep(2);

                    connection.incr(keyByte);
                    System.out.println(Thread.currentThread().toString()+ " " + System.currentTimeMillis());
                    //connection.exec();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }finally
                {
                    return null;

                }
            }
        }, false, true);

        //for (Object re : res)
        //{
        //    System.out.println(Thread.currentThread().toString() + re + " " + System.currentTimeMillis());
        //}
    }

    public void test_Pipelined(String key)
    {
        List<Object> res = redisTemplate.executePipelined(new RedisCallback<Long>()
        {
            public Long doInRedis(RedisConnection connection) throws DataAccessException
            {
                try
                {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyByte = serializer.serialize(key);

                    connection.incr(keyByte);
                    System.out.println(Thread.currentThread().toString()+ " " + System.currentTimeMillis());

                    Thread.currentThread().interrupt();
                    TimeUnit.SECONDS.sleep(3);


                    connection.incr(keyByte);
                    System.out.println(Thread.currentThread().toString()+ " " + System.currentTimeMillis());
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }finally
                {
                    return null;
                }

            }
        });

        for (Object re : res)
        {
            System.out.println(Thread.currentThread().toString() + re + " " + System.currentTimeMillis());
        }

    }


    public void test_set(String key, int newValue)
    {
        redisTemplate.execute(new RedisCallback<Integer>()
        {
            public Integer doInRedis(RedisConnection connection) throws DataAccessException
            {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(key);
                byte[] newValueByte = serializer.serialize(String.valueOf(newValue));

                connection.set(keyByte, newValueByte);
                System.out.println("test_set " + newValue + " " + Thread.currentThread().toString()  + " " + System.currentTimeMillis());
                try
                {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println(serializer.deserialize(connection.get(keyByte)));
                return null;
            }
        });


    }
}
