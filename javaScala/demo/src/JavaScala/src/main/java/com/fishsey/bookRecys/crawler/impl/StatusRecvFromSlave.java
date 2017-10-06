package com.fishsey.bookRecys.crawler.impl;

import com.fishsey.bookRecys.crawler.entity.Master;
import com.fishsey.bookRecys.crawler.enums.Priority;
import com.fishsey.bookRecys.crawler.exec.ControlCentor;
import com.fishsey.bookRecys.crawler.task.StatusHandleTask;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by root on 7/19/17.
 */

@Component
public class StatusRecvFromSlave implements MessageListener
{
    @Autowired
    ControlCentor controlCentor;
    @Autowired
    SendToSlave sendToSlave;
    @Autowired
    private Master master;


    /**
     * get url from slave, and send afterprocess-url to salve
     * */
    public void onMessage(Message message)
    {
        String status = new String(((ActiveMQBytesMessage) message).getContent().getData());

        System.out.println("receive status " + status);

        controlCentor.getPoolExecutor().execute(new StatusHandleTask(status,  Priority.statusTask.getLevel(), sendToSlave, master));
    }
}
