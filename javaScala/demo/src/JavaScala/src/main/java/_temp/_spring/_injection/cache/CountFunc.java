package _temp._spring._injection.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fishsey on 2017/9/28.
 */
@Component
public class CountFunc
{
    @Autowired
    CachedMap map;

    ReentrantLock lock = new ReentrantLock();

    public int func(int key)
    {
        if (map.cached.containsKey(key))
            return map.cached.get(key);
        else
            return  doTask(key);
    }
    private int doTask(int key)
    {
        if (map.unCached.putIfAbsent(key, lock.newCondition()) == null)//操作成功、actual do task
        {
            System.out.println("do task " + key);
            try
            {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            map.cached.put(key, key);
            map.unCached.get(key).signalAll();
            map.unCached.remove(key);
            return key;
        }
        else
        {
            try
            {
                map.unCached.get(key).wait();//wait for wake up

            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return map.cached.get(key);
    }
}
