package _learn.javaSe._io._rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: leizhimin
 * Date: 2008-8-7 22:21:07
 * 客户端测试，在客户端调用远程对象上的远程方法，并返回结果。
 */
public class HelloClient
{
    static ArrayList<Integer> slaves = new ArrayList<>();
    static {
        slaves.add(1);
        slaves.add(2);
        slaves.add(3);
    }
    public static void main(String args[])
    {
        try
        {
            //在 RMI服务注册表中查找名称为 RHello的对象，并调用其上的方法
            IHello rhello = (IHello) Naming.lookup("rmi://localhost:8888/RHello");

            System.out.println(rhello.helloWorld());
            System.out.println(rhello.sayHelloToSomeBody("熔岩"));
        } catch (NotBoundException e)
        {
            e.printStackTrace();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }
}
