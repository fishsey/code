package tools._usage;


import org.junit.Test;

import java.io.*;

/**
 * Created by root on 10/24/16.
 */
public class ObjectSerialize
{
    //object trans to string
    //object must extends Serializable
    public static <T extends Serializable> String encodeObj(T obj)
    {
        String s = "";
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(bout);
            out.writeObject(obj);
            out.flush();
            out.close();
            s = bout.toString("ISO-8859-1");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //s = URLEncoder.encode(s, "UTF-8");
        return s;
    }
    
    //string trans to object
    public static <T extends Serializable> T decodeString(String s)
    {
        T newObj = null;
        try
        {
            //s = URLDecoder.decode(s, "UTF-8");
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(s.getBytes("ISO-8859-1")));
            newObj = (T) in.readObject();
            in.close();
            return newObj;
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void test_()
    {
        Obj obj = new Obj("fishsey", 11);
        System.out.println(obj.hashCode());
        System.out.println(obj);

        String ss = ObjectSerialize.encodeObj(obj);
        obj = (Obj) ObjectSerialize.decodeString(ss);
        System.out.println(obj.hashCode());
        System.out.println(obj);

    }
}


class Obj implements Serializable
{
    public String name;
    transient public int value; //not serialize
    public Mytype type = new Mytype(); //class _usage.Mytype must implements Serializable

    @Override
    public String toString()
    {
        return "_usage.Obj{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", type=" + type +
                '}';
    }

    public Obj(String name, int value)
    {
        this.name = name;
        this.value = value;
    }
}

class Mytype implements Serializable
{
    public static String name = "this is a self-define class";

    @Override
    public String toString()
    {
        return "_usage.Mytype";
    }
}
