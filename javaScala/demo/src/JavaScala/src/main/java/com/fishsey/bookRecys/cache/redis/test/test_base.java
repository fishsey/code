package com.fishsey.bookRecys.cache.redis.test;

import com.fishsey.bookRecys.cache.redis.dao.impl.BaseDaoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by root on 8/13/17.
 */
@ContextConfiguration(locations = {"classpath*:com.fishsey.bookRecys/spring/spring-redis.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class test_base
{
    @Autowired
    private BaseDaoImpl baseDao;

    @Test
    public void test_()
    {
        boolean result = baseDao.addIfNotExist("11", "12");
        Assert.assertTrue(result);

    }
}
