package _temp._spring._injection._scheduleTaskHandler;

import java.lang.invoke.MethodHandle;

/**
 * Created by fishsey on 2017/9/24.
 */
public interface TaskHandler
{
    public final String taskMethodName = "doTask";
    public final Class<Boolean> retClass = boolean.class;
    public final Class<String> parasClass = String.class;

    public boolean doTask(String name);
    public MethodHandle getMethod();
}
