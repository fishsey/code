package com.fishsey.bookRecys.crawler.entity;

/**
 * Created by fishsey on 2017/8/30.
 */

import org.junit.Test;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleBloomFilter
{
    static class SimpleHash
    {
        private int cap;
        private int seed;

        //默认构造器，哈希表长默认为DEFAULT_SIZE大小，此哈希函数的种子为seed
        public SimpleHash(int cap, int seed)
        {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value)
        {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++)
            {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }

    public AtomicInteger states = new AtomicInteger(0);
    private static final int DEFAULT_SIZE = 1 << (16+6);
    private static final int[] seeds = new int[]{7, 11, 13, 31, 37, 61, 555, 3345};
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public SimpleBloomFilter()
    {
        for (int i = 0; i < seeds.length; i++)
        {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    //是否已经包含该 URL
    public boolean contains(String value)
    {
        if (value == null)
            return true;

        boolean flag;
        boolean ret = true;
        int[] hashes = new int[seeds.length];
        for (int i = 0; i < seeds.length; i++)
        {
            hashes[i] = func[i].hash(value);
        }

        //根据此 URL得到在布隆过滤器中的对应位，并判断其标志位（6个不同的哈希函数产生6种不同的映射）
        synchronized (bits)
        {
            for (int i = 0; i < seeds.length; i++)
            {
                flag = bits.get(hashes[i]);
                ret = ret && flag;
                bits.set(hashes[i]);
            }
        }

        if (ret == false)
            states.getAndIncrement();
        return ret;
    }


    @Test
    public void test_()
    {
        SimpleBloomFilter filter = new SimpleBloomFilter();

        HashSet<String> sets = new HashSet<>();
        for (int i = 0; i < Math.pow(10, 6); i++)
            sets.add(getRandomString(24));
        System.out.println(sets.size());

        Object[] strs = sets.toArray();

        ExecutorService poll = Executors.newFixedThreadPool(8);

        for (int i = 0; i < strs.length; i++)
        {
            int j = i;
            Runnable t1 = () -> filter.contains((String) strs[j]);
            poll.submit(t1);
        }
        poll.shutdown();
        while (!poll.isTerminated()) ;

        System.out.println(filter.states.get());
    }

    public static String getRandomString(int length)
    {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
