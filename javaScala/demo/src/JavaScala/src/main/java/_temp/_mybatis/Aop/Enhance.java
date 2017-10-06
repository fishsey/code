package _temp._mybatis.Aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by fishsey on 2017/9/7.
 */
@Aspect
@Component
public class Enhance
{
    @Before("execution(* _temp._mybatis.dao.PersonDao.updateNameById(..))")
    public void before()
    {
        System.out.println("add user before ... ... ...");
    }

    @Before("execution(* _temp._mybatis.Aop.TargetClassMybatis.test_update(..))")
    public void beforeTestTarget()
    {
        System.out.println("\nbeforeTest ... ... ...");
    }
}
