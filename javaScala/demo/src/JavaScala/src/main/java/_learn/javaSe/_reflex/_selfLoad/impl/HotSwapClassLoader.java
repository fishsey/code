package _learn.javaSe._reflex._selfLoad.impl;

import tools.usage.TransToByteArray;

/**
 * Created by root on 7/18/17.
 */
public class HotSwapClassLoader extends ClassLoader
{
    public HotSwapClassLoader()
    {
        super(HotSwapClassLoader.class.getClassLoader());
    }


    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException
    {
        byte[] classByte = TransToByteArray.loadClass(name);
        return defineClass(null, classByte, 0, classByte.length);
    }

    public Class loadByte(byte[] classByte)
    {
        return defineClass(null, classByte, 0, classByte.length);
    }
}
