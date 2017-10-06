package tools._usage;

/**
 * this is a class for type-check
 * Created by fishsey on 2016/7/12.
 */


import java.util.HashMap;
import java.util.Map;

public class TypeCheck
{
    public static void main(String[] args)
    {
        int i = 0;
        TypeObject to = new TypeObject();
        //1.反射
        //但是对于一个不确定类型的基本数据类型变量我们没法用反射来获取其类型。
        System.out.println("to的类型:" + to.getClass().getSimpleName());
        System.out.println(int.class.getSimpleName());
        System.out.println(Integer.class.getSimpleName());
        System.out.println("----------------------");

        //2.instanceof
        //但是这种办法貌似也没法确定基本数据类型
        if (to instanceof TypeObject)
        {
            System.out.println("to是TypeObject类型的");
        }
        System.out.println("----------------------");

        //以上两种方式对于对象，引用类型的都很好用，但是对基本数据类型就不那么好用了。
        //3.通过多态(方法的重载)
        System.out.println("i是：" + TypeTools.getType(i));
        System.out.println("to是：" + TypeTools.getType(to));
        System.out.println("\"cxyapi\"是：" + TypeTools.getType("www.cxyapi.com"));
    }
}

//定义一个类，为了演示引用类型的类型检测
class TypeObject
{
}

class TypeTools
{
    //获得类型
    public static Map<String, String> getType(Object o)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", o.getClass().getSimpleName());
        typeInfo.put("描述", "引用类型");
        return typeInfo;
    }

    public static Map<String, String> getType(int i)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", "int");
        typeInfo.put("描述", "整形");
        return typeInfo;
    }

    public static Map<String, String> getType(long l)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", "long");
        typeInfo.put("描述", "长整型");
        return typeInfo;
    }

    public static Map<String, String> getType(boolean b)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", "boolean");
        typeInfo.put("描述", "布尔类型");
        return typeInfo;
    }

    public static Map<String, String> getType(char b)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", "char");
        typeInfo.put("描述", "字符");
        return typeInfo;
    }

    public static Map<String, String> getType(float f)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", "float");
        typeInfo.put("描述", "单精度浮点型");
        return typeInfo;
    }

    public static Map<String, String> getType(double d)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", "double");
        typeInfo.put("描述", "双精度浮点型");
        return typeInfo;
    }

    public static Map<String, String> getType(String s)
    {
        Map<String, String> typeInfo = new HashMap<String, String>();
        typeInfo.put("类型", "String");
        typeInfo.put("描述", "字符串类型");
        return typeInfo;
    }

}

