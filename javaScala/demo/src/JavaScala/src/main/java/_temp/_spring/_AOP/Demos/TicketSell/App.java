package _temp._spring._AOP.Demos.TicketSell;

import _temp._spring._AOP.Demos.TicketSell.advice.AfterReturningAdvice;
import _temp._spring._AOP.Demos.TicketSell.advice.AroundAdvice;
import _temp._spring._AOP.Demos.TicketSell.advice.BeforeAdvice;
import _temp._spring._AOP.Demos.TicketSell.advice.ThrowsAdvice;
import _temp._spring._AOP.Demos.TicketSell.target.RailwayStation;
import _temp._spring._AOP.Demos.TicketSell.target.TicketService;
import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;

/**
 * 通过ProxyFactoryBean 手动创建 代理对象
 * Created by louis on 2016/4/14.
 */
public class App
{
    public static void main(String[] args) throws Exception
    {
        //1.针对不同的时期类型，提供不同的Advice
        Advice beforeAdvice = new BeforeAdvice();
        Advice afterReturningAdvice = new AfterReturningAdvice();
        Advice aroundAdvice = new AroundAdvice();
        Advice throwsAdvice = new ThrowsAdvice();


        //2.创建ProxyFactoryBean,用以创建指定对象的Proxy对象
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();


        //3.设置 Proxy的接口 , RealSubject
        RailwayStation railwayStation = new RailwayStation();//target  object
        proxyFactoryBean.setTarget(railwayStation);
        proxyFactoryBean.setInterfaces(TicketService.class);
        //RailwayStationAbs railwayStation = new RailwayStationAbs();//target  object
        //proxyFactoryBean.setInterfaces(TicketServiceAbs.class);


        //5.使用JDK基于接口实现机制的动态代理生成Proxy代理对象，如果想使用CGLIB，需要将这个flag设置成true
        proxyFactoryBean.setProxyTargetClass(true);

        //6.添加不同的 Advice, intercept all method invoker in the proxy-object
        proxyFactoryBean.addAdvice(afterReturningAdvice);
        proxyFactoryBean.addAdvice(beforeAdvice);
        proxyFactoryBean.addAdvice(aroundAdvice);
        //proxyFactoryBean.addAdvice(throwsAdvice);
        //proxyFactoryBean.setProxyTargetClass(false);

        //手动创建一个 pointcut,专门拦截 sellTicket方法
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution( * sellTicket(..))");
        //传入创建的 beforeAdvice和 pointcut
        FilteredAdvisor sellBeforeAdvior = new FilteredAdvisor(pointcut, beforeAdvice);
        //添加到FactoryBean中
        proxyFactoryBean.addAdvisor(sellBeforeAdvior);

        //通过ProxyFactoryBean生成
        TicketService ticketService = (TicketService) proxyFactoryBean.getObject();


        ticketService.sellTicket();
        System.out.println("---------------------------------------------------------------------------------------");
        ticketService.inquire();

    }


}
