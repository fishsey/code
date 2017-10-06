package com.fishsey.bookRecys.crawler.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by root on 7/18/17.
 */
@Component
public class Master
{
    //string: name
    private  HashMap<String, Slave> slaves = new HashMap<>();
    //string: all has crawled urls
    private ConcurrentHashMap<String, Boolean> crawledUrls = new ConcurrentHashMap<>();

    @Autowired
    AllocationTree allocationTree;

    AtomicInteger count = new AtomicInteger(0);

    public AllocationTree getAllocationTree()
    {
        return allocationTree;
    }

    public HashMap<String, Slave> getSlaves()
    {
        return slaves;
    }

    public void addSlave(Slave slave)
    {
        slaves.put(slave.name, slave);
        allocationTree.addSlave(slave);
    }


    public String getQueue(String url)
    {
        if (crawledUrls.putIfAbsent(url, Boolean.TRUE) != null)
        {
            System.out.println(url + ".... has crawled already  " + count.getAndIncrement());
            return null; //if already exist
        }
        return allocationTree.findSmallerLarger(url);
    }

}
