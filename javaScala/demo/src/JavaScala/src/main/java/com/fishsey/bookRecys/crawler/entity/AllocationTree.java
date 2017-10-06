package com.fishsey.bookRecys.crawler.entity;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TreeMap;

/**
 * Created by root on 7/19/17.
 */
@Component
public class AllocationTree
{
    private TreeMap<Integer, String> treeMap = new TreeMap<>();

    public TreeMap<Integer, String> getTreeMap()
    {
        return treeMap;
    }

    public void addSlave(Slave slave)
    {
        treeMap.put(slave.hash, slave.urlQueueName);
        for (Integer integer : slave.logicHash)
        {
            treeMap.put(integer, slave.urlQueueName);
        }
    }

    public Set<Integer> getAllExistHash()
    {
        return treeMap.keySet();
    }


    public String findSmallerLarger(String url)
    {
        return find(hash(url));
    }

    public String find(Integer hash)
    {
        return "wangyi.url.slave.wangyiNews_0";

        //Integer leastLarger = treeMap.higherKey(hash);
        //if (leastLarger == null)
        //{
        //    return treeMap.firstEntry().getValue();
        //}
        //else
        //{
        //    return treeMap.get(leastLarger);
        //}
    }

    private Integer hash(String url)
    {
        return url.hashCode();

    }
}
