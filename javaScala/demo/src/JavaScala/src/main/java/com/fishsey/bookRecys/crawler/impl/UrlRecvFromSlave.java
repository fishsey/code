package com.fishsey.bookRecys.crawler.impl;

import com.fishsey.bookRecys.crawler.entity.Master;
import com.fishsey.bookRecys.crawler.enums.Priority;
import com.fishsey.bookRecys.crawler.exec.ControlCentor;
import com.fishsey.bookRecys.crawler.task.SendUrlTask;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by root on 7/17/17.
 */

@Component
public class UrlRecvFromSlave implements MessageListener
{
    @Autowired
    ControlCentor controlCentor;
    @Autowired
    Master master;
    @Autowired
    SendToSlave sendToSlave;

    /**
    * get url from slave, and send afterprocess-url to salve
    * */
    public void onMessage(Message message)
    {
        String url = new String(((ActiveMQBytesMessage) message).getContent().getData());

        String queuename = master.getQueue(url);

        if (queuename != null)
            controlCentor.getPoolExecutor().execute(new SendUrlTask(url, queuename,  Priority.urlTask.getLevel(), sendToSlave));
    }

}
