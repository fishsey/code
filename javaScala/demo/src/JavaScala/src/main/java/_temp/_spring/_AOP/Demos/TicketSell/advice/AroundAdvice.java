package _temp._spring._AOP.Demos.TicketSell.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * AroundAdvice
 * Created by louis on 2016/4/15.
 */
public class AroundAdvice implements MethodInterceptor
{
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        System.out.println("AROUND_ADVICE:BEGIN....");

        Object returnValue = invocation.proceed();

        System.out.println("AROUND_ADVICE:END.....");

        return returnValue;
    }
}
