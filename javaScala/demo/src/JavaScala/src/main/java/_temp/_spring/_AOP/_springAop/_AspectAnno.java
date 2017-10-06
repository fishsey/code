package _temp._spring._AOP._springAop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by root on 6/1/17.
 */

@Aspect
@Component
public class _AspectAnno
{
    @Before("execution(* _temp._spring._AOP._springAop.TargetClass.addUser(..))")
    public void before()
    {
        System.out.println("add user before ... ... ...");
    }


    @After("execution(* _temp._spring._AOP._springAop.TargetClass.addUser(..))")
    public void after()
    {
        System.out.println("add user after ... ... ...");
        System.out.println();
    }


    @AfterReturning(returning = "text", pointcut = ("execution(* _temp._spring._AOP._springAop.TargetClass.get(..))"))
    public void afterReturn(StringBuffer text)
    {
        System.out.println("afterReturn: " + text);
        text.append("___new");
    }

    @Around("execution(* _temp._spring._AOP._springAop.TargetClass.function(..))")
    public void aroundFunc(ProceedingJoinPoint processFunc) throws Throwable
    {
        System.out.println("begin transaction...");

        // 回调原来的目标方法
        processFunc.proceed();

        System.out.println("end transaction...");
        System.out.println();

    }
}
