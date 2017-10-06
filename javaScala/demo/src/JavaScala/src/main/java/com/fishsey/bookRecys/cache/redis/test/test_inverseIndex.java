package com.fishsey.bookRecys.cache.redis.test;

import com.fishsey.bookRecys.cache.redis.dao.impl.InverseIndexDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by root on 8/12/17.
 */

@ContextConfiguration(locations = {"classpath*:com.fishsey.bookRecys/spring/spring-redis.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class test_inverseIndex
{
    @Autowired
    InverseIndexDao inverseIndexDao;

    @Test
    public void test_add()
    {
        boolean result = inverseIndexDao.add(1, 1, 0.1);
        result = inverseIndexDao.add(1, 2, 0.2);
        result = inverseIndexDao.add(1, 3, 0.3);
        result = inverseIndexDao.add(1, 4, 0.4);
        Assert.assertTrue(result);

    }


    @Test
    public void test_check()
    {
        inverseIndexDao.getRange(1, 0, 3).forEach(System.out::println);
    }
}
