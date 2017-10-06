package _learn.javaSe._reflex._basic.test;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishsey on 2017/3/20.
 */
public class Genericity
{
    public List<String> stringList = new ArrayList<>();

    public List<String> getStringList()
    {
        return this.stringList;
    }


    @Test
    public  void testGenericityMethond()
    {
        try
        {
            Method method = Genericity.class.getMethod("getStringList", null);
            Type returnType = method.getGenericReturnType();

            //返回值的类型
            System.out.println(returnType.getTypeName());

            //返回值中泛型的类型
            if(returnType instanceof ParameterizedType)
            {
                ParameterizedType type = (ParameterizedType) returnType;
                Type[] typeArguments = type.getActualTypeArguments();
                for (Type typeArgument : typeArguments)
                {
                    //返回值中泛型的类型
                    System.out.println(typeArgument.getTypeName());
                }
            }
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

}
