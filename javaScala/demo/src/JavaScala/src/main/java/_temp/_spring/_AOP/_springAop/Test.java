package _temp._spring._AOP._springAop;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-aop.xml"})
public class Test
{
    @Autowired
    TargetClass hello;

    @org.junit.Test
    public void test_()
    {
        hello.get("newString");
        System.out.println(hello.getText());
    }

    @org.junit.Test
    public void test_2()
    {
        hello.function();
    }
}

