package com.fishsey.bookRecys.crawler.enums;

/**
 * Created by root on 7/19/17.
 */
public enum CrawlStatus
{
    dead(9),
    ready(0),
    running(1),
    waitingTask(2),
    dataProcess(3);

    private final int status;
    CrawlStatus(int status)
    {
        this.status = status;
    }
    public int getStatus()
    {
        return status;
    }


}
