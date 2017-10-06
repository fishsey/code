package com.fishsey.bookRecys.crawler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by root on 7/17/17.
 */


@Component
public class SendToSlave
{
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;


    public void send(String queueName, final String message)
    {
        jmsTemplate.send(queueName, new MessageCreator()
        {
            public Message createMessage(Session session) throws JMSException
            {
                return session.createTextMessage(message);
            }
        });
    }


}
