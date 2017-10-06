package com.fishsey.bookRecys.crawler.task;

import com.fishsey.bookRecys.crawler.entity.Master;
import com.fishsey.bookRecys.crawler.entity.Slave;
import com.fishsey.bookRecys.crawler.impl.SendToSlave;

import java.util.Set;
import java.util.UUID;

/**
 * Created by root on 7/19/17.
 */
public class StatusHandleTask extends Task implements Runnable
{
    public String message;
    private Master master;
    private SendToSlave sendToSlave;


    public StatusHandleTask(String message, int priority, SendToSlave sendToSlave, Master master)
    {
        super(priority);
        this.message = message;
        this.sendToSlave = sendToSlave;
        this.master = master;
    }

    @Override
    public void run()
    {
        int status = this.message.charAt(0) - '0';
        switch (status)
        {
            case 0:
                updateMaster();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 9:
                break;

        }
    }

    private void updateMaster()
    {
        Slave slave = addSalve();
        sendToSlave.send(slave.statusQueueName, "1");
    }

    private Slave addSalve()
    {
        String name = getName();
        synchronized (master)
        {
            if (! master.getSlaves().containsKey(name))
            {
                Slave crawl = parseGenSlave();
                master.addSlave(crawl);
                System.out.println(crawl);
                return crawl;
            }
        }
        return master.getSlaves().get(name);
    }

    private Slave parseGenSlave()
    {
        Slave crawl = new Slave();
        String[] info = this.message.substring(2, this.message.length()-1).split(":");

        crawl.setName(info[0]);
        crawl.setStartShellPath(info[1]);
        crawl.setUrlQueueName(info[2]);
        crawl.setStatusQueueName(info[3]);

        crawl.setStatus(1);

        int number = crawl.getLocalhashNums()+1;
        Set<Integer> hashes = master.getAllocationTree().getAllExistHash();
        while (number > 0)
        {
            int hash = UUID.randomUUID().hashCode();

           if (!hashes.contains(hash))
           {
               if (number == 1)
                   crawl.setHash(hash);
               else
                   crawl.getLogicHash().add(hash);
               number--;
           }
        }
        return crawl;
    }

    private String getName()
    {
        return this.message.substring(2, this.message.length()-1).split(":")[0];
    }
}
