package _temp._spring._AOP._aspect.overrideFinalMethod;

import _temp._spring._AOP.Demos.TicketSell.advice.AroundAdvice;
import _temp._spring._AOP._aspect.overrideFinalMethod.entity.ChildInherit;
import _temp._spring._AOP._aspect.overrideFinalMethod.entity.ChildInterface;
import _temp._spring._AOP._aspect.overrideFinalMethod.entity.FatherInterface;
import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

/**
 * Created by fishsey on 2017/8/24.
 */
public class test
{
    @Test
    public void test_Aspect()
    {
        ChildInherit hello = new ChildInherit();
        hello.print();
    }

    @Test
    public void test_Str()
    {
        String s = "hello";
        s.toString();
        s.charAt(1);
        //System.out.println(s);
        //System.out.println(s.charAt(1));
    }

    @Test
    public void test_JdkProxy()
    {
        ChildInterface hello = new ChildInterface();

        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(hello);
        proxyFactoryBean.setInterfaces(FatherInterface.class);
        proxyFactoryBean.setProxyTargetClass(false);

        Advice aroundAdvice = new AroundAdvice();
        proxyFactoryBean.addAdvice(aroundAdvice);

        //AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //pointcut.setExpression("execution(* _temp._spring._AOP._aspect.overrideFinalMethod.entity.Child.print(..))");
        //FilteredAdvisor aroundAdvior = new FilteredAdvisor(pointcut, aroundAdvice);
        //proxyFactoryBean.addAdvisor(aroundAdvior);

        FatherInterface child = (FatherInterface) proxyFactoryBean.getObject();

        child.print();
    }
}
