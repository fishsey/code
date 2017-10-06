package _temp._spring._injection._scheduleTaskHandler.impl;

import _temp._spring._injection._scheduleTaskHandler.TaskHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fishsey on 2017/9/20.
 */
public class _Map
{
    public HashMap<String, String> tasks = new HashMap<>();
    public HashMap<String, TaskHandler> handlers;
    public ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);


    public HashMap<String, TaskHandler> getHandlers()
    {
        return handlers;
    }
    public void setHandlers(HashMap<String, TaskHandler> handlers)
    {
        this.handlers = handlers;
    }

    public HashMap<String, String> getTasks()
    {
        return tasks;
    }
    public void setTasks(HashMap<String, String> tasks)
    {
        this.tasks = tasks;
    }

    public _Map()
    {
        init();
    }
    private void init()
    {
        Runnable task = () -> {
            try
            {
                this.doTasks();
            } catch (Throwable throwable)
            {
                throwable.printStackTrace();
            }
        };
        pool.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);
    }

    public void  doTasks() throws Throwable
    {
        for (Map.Entry<String, String> entry : tasks.entrySet())
        {
            String tasks = entry.getKey();
            String name = entry.getValue();
            handlers.get(tasks).getMethod().invokeWithArguments(handlers.get(tasks), name);
        }
    }
}
