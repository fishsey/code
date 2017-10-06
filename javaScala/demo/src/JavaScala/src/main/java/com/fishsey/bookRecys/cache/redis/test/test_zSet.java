package com.fishsey.bookRecys.cache.redis.test;

import com.fishsey.bookRecys.cache.redis.dao.impl.ZsetDao;
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
public class test_zSet
{
    @Autowired
    private ZsetDao baseDao;

    @Test
    public void test_() throws InterruptedException
    {
		//baseDao.test_add("");
        String s = "杭州";
        String start = s;
        String end = getEnd(s);

        baseDao.test_add(end);

        System.out.println();
    }

    public String getEnd(String s)
    {
        String s1 = s.substring(0, s.length()-1);
        String s2 = s.substring(s.length()-1, s.length());
        byte[] bytes = s2.getBytes();
        bytes[2] = (byte) (new Byte(bytes[2]).intValue() + 1);
        return  (s1 + new String(bytes));
    }
}
