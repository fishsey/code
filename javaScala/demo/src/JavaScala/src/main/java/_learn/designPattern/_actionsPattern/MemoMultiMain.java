package _learn.designPattern._actionsPattern;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 4/23/17.
 */
public class MemoMultiMain
{
    public static void main(String[] args)
    {
        //ori keep current state
        Originator2 ori = new Originator2();
        //caretaker keep history state
        Caretaker2 caretaker = new Caretaker2();

        ori.setState1("中国");
        ori.setState2("强盛");
        ori.setState3("繁荣");
        System.out.println("===初始化状态=== " + ori);

        caretaker.setMemento("001", ori.createMemento());

        ori.setState1("软件");
        ori.setState2("架构");
        ori.setState3("优秀");
        System.out.println("===修改后状态=== " + ori);

        ori.restoreMemento(caretaker.getMemento("001"));
        System.out.println("===恢复后状态=== " + ori);
    }
}


class Originator2
{
    private String state1 = "";
    private String state2 = "";
    private String state3 = "";

    public String getState1()
    {
        return state1;
    }

    public void setState1(String state1)
    {
        this.state1 = state1;
    }

    public String getState2()
    {
        return state2;
    }

    public void setState2(String state2)
    {
        this.state2 = state2;
    }

    public String getState3()
    {
        return state3;
    }

    public void setState3(String state3)
    {
        this.state3 = state3;
    }

    public Memento2 createMemento()
    {
        return new Memento2(BeanUtils.backupProp(this));
    }

    public void restoreMemento(Memento2 memento)
    {
        BeanUtils.restoreProp(this, memento.getStateMap());
    }

    public String toString()
    {
        return "state1=" + state1 + "state2=" + state2 + "state3=" + state3;
    }
}

class Memento2
{
    private Map stateMap;

    public Memento2(Map map)
    {
        this.stateMap = map;
    }

    public Map getStateMap()
    {
        return stateMap;
    }

    public void setStateMap(Map stateMap)
    {
        this.stateMap = stateMap;
    }
}

class BeanUtils
{
    public static Map backupProp(Object bean)
    {
        Map result = new HashMap();
        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor des : descriptors)
            {
                String fieldName = des.getName();
                Method getter = des.getReadMethod();
                Object fieldValue = getter.invoke(bean, new Object[]{});
                if (!fieldName.equalsIgnoreCase("class"))
                {
                    result.put(fieldName, fieldValue);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static void restoreProp(Object bean, Map propMap)
    {
        try
        {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor des : descriptors)
            {
                String fieldName = des.getName();
                if (propMap.containsKey(fieldName))
                {
                    Method setter = des.getWriteMethod();
                    setter.invoke(bean, new Object[]{propMap.get(fieldName)});
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class Caretaker2
{
    private Map memMap = new HashMap();

    public Memento2 getMemento(String index)
    {
        return (Memento2) memMap.get(index);
    }

    public void setMemento(String index, Memento2 memento)
    {
        this.memMap.put(index, memento);
    }
}
