package _temp._spring._applicationContext._get;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Locale;
/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */

/*
* Spring容器会检测容器中所有 Bean，如果发现某个 Bean实现了 ApplicationContextAware 接口，
* Spring容器会在创建该 Bean之后
* 自动调用该 Bean的 setApplicationContext()方法
* 调用该方法时，会将容器本身作为参数传给该方法。
* */
public class Person implements ApplicationContextAware
{
	private ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext ctx)
		throws BeansException
	{
		this.ctx = ctx;
	}
	public void sayHi(String name)
	{
		System.out.println(ctx.getMessage("hello" , new String[]{name}, Locale.getDefault(Locale.Category.FORMAT)));
		System.out.println(ctx.getMessage("hello2" , new String[]{name}, Locale.getDefault(Locale.Category.FORMAT)));
	}
}
