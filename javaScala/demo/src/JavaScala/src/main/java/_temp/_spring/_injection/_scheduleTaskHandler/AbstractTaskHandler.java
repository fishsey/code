package _temp._spring._injection._scheduleTaskHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by fishsey on 2017/9/28.
 */
public abstract class AbstractTaskHandler implements TaskHandler
{
    public MethodHandle method = null;

    public AbstractTaskHandler()
    {
        init();
    }

    public void init()
    {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try
        {
            method = lookup.findVirtual(TaskHandler.class, TaskHandler.taskMethodName, MethodType.methodType(TaskHandler.retClass, TaskHandler.parasClass));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public MethodHandle getMethod()
    {
        return this.method;
    }
}
