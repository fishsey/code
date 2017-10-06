package com.fishsey.bookRecys.cache.redis.test;

import com.fishsey.bookRecys.cache.redis.dao.impl.ConCurrenceDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 8/13/17.
 */
@ContextConfiguration(locations = {"classpath*:com.fishsey.bookRecys/spring/spring-redis.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class test_CC
{
    @Autowired
    private ConCurrenceDao baseDao;

    @Test
    public void test_() throws InterruptedException
    {
        Runnable runnable = () -> baseDao.test_add("num");

        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i=0; i<1000; i++)
        {
            pool.execute(runnable);
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
