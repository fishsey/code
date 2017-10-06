package _temp._spring._applicationContext._eventListener;

import org.springframework.context.ApplicationEvent;

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
public class EmailEvent extends ApplicationEvent
{
    private String address;
    private String text;

    public EmailEvent(Object source)
    {
        super(source);
    }

    public EmailEvent(Object source, String address, String text)
    {
        super(source);
        this.address = address;
        this.text = text;
    }

    // address��setter��getter����
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getAddress()
    {
        return this.address;
    }

    // text��setter��getter����
    public void setText(String text)
    {
        this.text = text;
    }
    public String getText()
    {
        return this.text;
    }
}
