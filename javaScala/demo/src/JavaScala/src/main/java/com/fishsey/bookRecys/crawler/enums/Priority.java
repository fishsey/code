package com.fishsey.bookRecys.crawler.enums;

/**
 * Created by root on 7/18/17.
 */
public enum  Priority 
{
    statusTask(1),
    urlTask(3);

    private int level;
    Priority(int level)
    {
        this.level = level;
    }
    public int getLevel()
    {
        return level;
    }

}
