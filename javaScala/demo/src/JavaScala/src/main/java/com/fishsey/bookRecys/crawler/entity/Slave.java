package com.fishsey.bookRecys.crawler.entity;

import java.util.ArrayList;

/**
 * Created by root on 7/18/17.
 */
public class Slave
{
    public String name;
    public String startShellPath;
    public String urlQueueName;
    public String statusQueueName;

    public int status;
    public int hash;
    public int localhashNums = 16;
    public ArrayList<Integer> logicHash = new ArrayList<>();


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStartShellPath()
    {
        return startShellPath;
    }

    public void setStartShellPath(String startShellPath)
    {
        this.startShellPath = startShellPath;
    }

    public String getUrlQueueName()
    {
        return urlQueueName;
    }

    public void setUrlQueueName(String urlQueueName)
    {
        this.urlQueueName = urlQueueName;
    }

    public String getStatusQueueName()
    {
        return statusQueueName;
    }

    public void setStatusQueueName(String statusQueueName)
    {
        this.statusQueueName = statusQueueName;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getHash()
    {
        return hash;
    }

    public void setHash(int hash)
    {
        this.hash = hash;
    }

    public int getLocalhashNums()
    {
        return localhashNums;
    }

    public void setLocalhashNums(int localhashNums)
    {
        this.localhashNums = localhashNums;
    }

    public ArrayList<Integer> getLogicHash()
    {
        return logicHash;
    }

    public void setLogicHash(ArrayList<Integer> logicHash)
    {
        this.logicHash = logicHash;
    }

    @Override
    public String toString()
    {
        return "Slave{" +
                "name='" + name + '\'' +
                ", startShellPath='" + startShellPath + '\'' +
                ", urlQueueName='" + urlQueueName + '\'' +
                ", statusQueueName='" + statusQueueName + '\'' +
                ", status=" + status +
                ", hash=" + hash +
                ", localhashNums=" + localhashNums +
                ", logicHash=" + logicHash +
                '}';
    }
}
