package com.fishsey.bookRecys.crawler.task;

import com.fishsey.bookRecys.crawler.impl.SendToSlave;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by root on 7/18/17.
 */

public class SendUrlTask extends Task implements Runnable
{
    @Autowired
    private SendToSlave sendToSlave;

    public String url;
    public String queueName;


    public SendUrlTask(String url, String queueName, int priority, SendToSlave sendToSlave)
    {
        super(priority);
        this.url = url;
        this.queueName = queueName;
        this.sendToSlave = sendToSlave;
    }

    @Override
    public void run()
    {
        //System.out.println(this);
        sendToSlave.send(queueName, url);
    }

    @Override
    public String toString()
    {
        return "SendUrlTask{" +
                "sendToSlave=" + sendToSlave +
                ", url='" + url + '\'' +
                ", queueName='" + queueName + '\'' +
                '}';
    }
}
