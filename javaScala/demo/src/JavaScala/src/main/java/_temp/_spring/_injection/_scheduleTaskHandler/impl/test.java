package _temp._spring._injection._scheduleTaskHandler.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

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
public class test implements ApplicationContextAware
{
    ApplicationContext applicationContext;

    @Autowired
    _Map mapTasks;

    @Test
    public void test_() throws Throwable
    {
        mapTasks.getTasks().put("1", "name1");
        mapTasks.getTasks().put("2", "name2");
        TimeUnit.SECONDS.sleep(3);

        mapTasks.getTasks().put("1", "name1-1");
        mapTasks.getTasks().put("2", "name1-2");
        TimeUnit.SECONDS.sleep(1);

        mapTasks.pool.shutdown();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
