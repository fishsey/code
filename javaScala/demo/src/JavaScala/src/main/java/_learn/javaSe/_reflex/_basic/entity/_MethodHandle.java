package _learn.javaSe._reflex._basic.entity;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class _MethodHandle
{
    MethodHandles.Lookup lookup = MethodHandles.lookup();;
    MethodHandle mapSize;
    MethodHandle mapGetByKey;

    @Test
    public void test_() throws Throwable
    {
        init();

        HashMap<Integer, Integer> maps = new HashMap<>();
        maps.put(1, 11);
        //System.out.println(mapSize.invoke(maps));
        System.out.println(mapGetByKey.invoke(maps, 1));


        //HashMap<Integer, Integer> treeMap = new HashMap<>();
        //treeMap.put(1, 11);
        //treeMap.put(2, 22);
        //System.out.println(mapSize.invoke(treeMap));
        //System.out.println(mapGetByKey.invoke(treeMap, 1));
    }

    public void init() throws NoSuchMethodException, IllegalAccessException
    {
        mapSize = lookup.findVirtual(Map.class, "size", MethodType.methodType(int.class));
        mapGetByKey = lookup.findVirtual(Map.class, "get", MethodType.methodType(Object.class, Object.class));
    }


    @Test
    public void test_2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method mapSizeRef = Map.class.getMethod("get", Object.class);

        HashMap<Integer, Integer> maps = new HashMap<>();
        maps.put(1, 11);

        System.out.println(mapSizeRef.invoke(maps, 1));
    }
}
