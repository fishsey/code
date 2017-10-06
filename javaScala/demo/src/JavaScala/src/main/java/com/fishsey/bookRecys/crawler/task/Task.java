package com.fishsey.bookRecys.crawler.task;

/**
 * Created by root on 7/18/17.
 */
public class Task implements Comparable<Task>
{
    public int priority;

    public Task()
    {
    }

    public Task(int priority)
    {
        this.priority = priority;
    }

    /*
    * small-heap, so 1 first exec than 2
    * */
    @Override
    public int compareTo(Task obj)
    {
        return Integer.compare(this.priority, obj.priority);
    }
}
