package _temp._spring._AOP.Demos.TicketSell.advice;


import java.lang.reflect.Method;

/**
 * 返回结果时后的处理意见
 * Created by louis on 2016/4/14.
 */
public class AfterReturningAdvice implements org.springframework.aop.AfterReturningAdvice
{
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable
    {
        System.out.println("AFTER_RETURNING:本次服务已结束....");
    }
}
