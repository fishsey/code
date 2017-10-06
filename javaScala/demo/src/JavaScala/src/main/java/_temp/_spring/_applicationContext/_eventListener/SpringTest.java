package _temp._spring._applicationContext._eventListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest
{
    public static void main(String[] args)
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        EmailEvent ele = new EmailEvent("test", "spring_test@163.com", "this is a test");

        ctx.publishEvent(ele);
    }
}
