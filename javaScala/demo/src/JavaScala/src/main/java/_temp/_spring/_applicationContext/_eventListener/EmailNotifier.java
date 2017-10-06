package _temp._spring._applicationContext._eventListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

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
public class EmailNotifier implements ApplicationListener
{
    @Override
    public  void onApplicationEvent(ApplicationEvent evt)
    {
        if (evt instanceof EmailEvent)
        {
            EmailEvent emailEvent = (EmailEvent) evt;
            System.out.println("the email address: " + emailEvent.getAddress());
            System.out.println("the email text: " + emailEvent.getText());
        } else
        {
            System.out.println("others actions: " + evt);
        }
    }

}
