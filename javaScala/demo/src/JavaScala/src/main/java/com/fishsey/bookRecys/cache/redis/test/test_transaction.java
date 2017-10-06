package com.fishsey.bookRecys.cache.redis.test;

import com.fishsey.bookRecys.cache.redis.dao.impl.TransactionDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by root on 8/17/17.
 */
@ContextConfiguration(locations = {"classpath*:com.fishsey.bookRecys/spring/spring-redis.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class test_transaction
{
    @Autowired
    private TransactionDao transactionDao;

    @Test
    public void test_pipeline() throws InterruptedException
    {
        Thread t1 = new Thread(() -> transactionDao.test_pipeline("num"));
        t1.start();

        //Thread t2 = new Thread(() -> transactionDao.test_pipeline("num"));
        //t2.start();

        t1.join();
        //t2.join();
    }

    @Test
    public void test_Pipelined() throws InterruptedException
    {
        Thread t1 = new Thread(() -> transactionDao.test_Pipelined("num"));
        t1.start();
        //Thread t2 = new Thread(() -> transactionDao.test_Pipelined("num"));
        //t2.start();
        //
        //TimeUnit.SECONDS.sleep(1);
        //transactionDao.test_set("num", 2);
        //transactionDao.test_set("num", 3);

        t1.join();
        //t2.join();
    }

    @Test
    public void test_test_set() throws InterruptedException
    {
        Thread t1 = new Thread(() -> transactionDao.test_set("num", 2));
        t1.start();
        Thread t2 = new Thread(() -> transactionDao.test_set("num", 3));
        t2.start();

        t1.join();
        t2.join();
    }
}
