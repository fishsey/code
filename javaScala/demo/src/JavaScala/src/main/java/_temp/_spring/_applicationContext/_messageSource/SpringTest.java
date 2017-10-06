package _temp._spring._applicationContext._messageSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Locale;

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
public class SpringTest
{
    public static void main(String[] args) throws Exception
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");

        String hello = ctx.getMessage("hello", new String[]{"shun wukong"}, Locale.getDefault(Locale.Category.FORMAT));
        String now = ctx.getMessage("now", new Object[]{new Date()}, Locale.getDefault(Locale.Category.FORMAT));

        System.out.println(hello);
        System.out.println(now);
    }
}
