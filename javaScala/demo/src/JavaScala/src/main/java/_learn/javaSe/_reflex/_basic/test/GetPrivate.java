package _learn.javaSe._reflex._basic.test;

import _learn.javaSe._reflex._basic.entity.PrivateEntity;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by fishsey on 2017/3/20.
 */
public class GetPrivate
{
    @Test
    public  void getPrivateField()
    {
        try
        {
            PrivateEntity obj = new PrivateEntity();
            Field privateField =  obj.getClass().getDeclaredField("priStr");
            privateField.setAccessible(true);
            System.out.println(privateField.get(obj));

        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getPrivateMethod()
    {
        try
        {
            PrivateEntity obj = new PrivateEntity();
            Method privateMethod =  obj.getClass().getDeclaredMethod("privateMethod", String.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(obj, "tempTest");

        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
