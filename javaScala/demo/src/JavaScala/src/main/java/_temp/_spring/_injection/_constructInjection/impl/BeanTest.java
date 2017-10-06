package _temp._spring._injection._constructInjection.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-beans.xml"})
public class BeanTest
{
    @Test
    public void test_()
    {
        Chinese china;
    }
}
