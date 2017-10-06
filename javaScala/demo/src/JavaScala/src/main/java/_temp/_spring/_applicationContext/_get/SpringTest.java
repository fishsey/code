package _temp._spring._applicationContext._get;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest
{
	public static void main(String[] args)throws Exception
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		Person p = ctx.getBean("person_get" , Person.class);
		p.sayHi("get application context");
	}
}
