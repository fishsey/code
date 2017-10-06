package com.fishsey.bookRecys.crawler.exec;

import com.fishsey.bookRecys.crawler.entity.Master;
import com.fishsey.bookRecys.crawler.enums.Priority;
import com.fishsey.bookRecys.crawler.impl.SendToSlave;
import com.fishsey.bookRecys.crawler.task.SendUrlTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 7/17/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com.fishsey.bookRecys/spring/SpringActiveMQ-config.xml"})
@Component
public class ControlCentor
{
    //private static final String initUrlsFile = "bookRecys/tempData/initUrls";
    private static final String initUrlsFile = "tempData/initUrls";
    private static ArrayList<String> initUrls;
    static {
        initUrls = getUrlsFromFile(initUrlsFile);
    }
    private static ArrayList<String> getUrlsFromFile(String initUrlsFile)
    {
        ArrayList<String> result = new ArrayList<>();
        try
        {
            BufferedReader cin = new BufferedReader(new FileReader(initUrlsFile));
            cin.lines().forEach(url -> result.add(url));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    private void sendInitUrlsToAllSlaves()
    {
        initUrls.stream().limit(100).forEach(url -> poolExecutor.execute(new SendUrlTask(url, master.getQueue(url), Priority.urlTask.getLevel(), sendToSlave)));
    }




    @Autowired
    Master master;
    @Autowired
    ExecShell execShell;
    @Autowired
    SendToSlave sendToSlave;


    public ThreadPoolExecutor getPoolExecutor()
    {
        return poolExecutor;
    }

    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 40, 30, TimeUnit.MINUTES, new PriorityBlockingQueue<>());

    public void destroy()
    {
        poolExecutor.shutdown();
    }
    public void destroyNow()
    {
        poolExecutor.shutdownNow();
    }


    @Test
    public void test() throws InterruptedException
    {
        while (true)
        {
            TimeUnit.SECONDS.sleep(10);
        }
    }


}
