package _temp._mybatis.test;

import _temp._mybatis.Aop.TargetClassMybatis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by fishsey on 2017/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-mybatis.xml","classpath:_temp/spring/spring-aop.xml"})
public class _AopTest
{
    @Autowired
    TargetClassMybatis hello;


    @Test
    public void test_update() throws IOException
    {
        hello.test_update();
    }
}
