package _temp._spring._injection.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-beans.xml"})
public class test
{

    @Autowired
    CountFunc countFunc;

    @Test
    public void test_() throws Throwable
    {
        Runnable r1 = () -> countFunc.func(1);
        Runnable r2 = () -> countFunc.func(2);
        Runnable r3 = () -> countFunc.func(3);

        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i=0; i<10; i++)
        {
            pool.submit(r1);
            pool.submit(r1);
            pool.submit(r1);
            pool.submit(r1);
            pool.submit(r1);
            pool.submit(r2);
            pool.submit(r2);
            pool.submit(r2);
            pool.submit(r2);
            pool.submit(r3);
            pool.submit(r3);
            pool.submit(r3);
            pool.submit(r3);
        }

        pool.shutdown();
        while (!pool.isTerminated());
        System.out.println(countFunc.map.cached.size());
    }

}
