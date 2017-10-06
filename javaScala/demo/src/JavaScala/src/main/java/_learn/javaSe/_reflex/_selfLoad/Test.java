package _learn.javaSe._reflex._selfLoad;

import _learn.javaSe._reflex._selfLoad.impl.HotSwapClassLoader;
import _learn.javaSe._reflex._selfLoad.impl._Interface;
import tools.usage.TransToByteArray;

/**
 * Created by root on 7/12/17.
 */
public class Test
{
    _Interface o1 = new _Object();

    public void setO1(_Interface o1)
    {
        this.o1 = o1;
    }

    public void test()
    {
        System.out.println(o1.getClass().getClassLoader().toString() + "\t" + o1);
    }

    @org.junit.Test
    public void test_1() throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        Test tst = new Test();
        tst.test();

        //re-load class and update object
        //String path = "/root/AAA/code/idea/demos/target/classes/com/fishsey/javaSe/_reflex/_selfLoad/_Object.class";
        String path = "C:\\Users\\ally1\\Desktop\\code\\javaScala\\demo\\target\\classes\\_learn\\javaSe\\_reflex\\_selfLoad\\_Object.class";
        byte[] data = TransToByteArray.loadClass(path);

        o1 = (_Interface) new HotSwapClassLoader().loadByte(data).newInstance();
        tst.setO1(o1);
        tst.test();

        _Interface o3 = (_Interface) new HotSwapClassLoader().findClass(path).newInstance();
        tst.setO1(o3);
        tst.test();

    }
}
