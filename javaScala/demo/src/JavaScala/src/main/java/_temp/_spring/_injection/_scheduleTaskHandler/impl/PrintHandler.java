package _temp._spring._injection._scheduleTaskHandler.impl;

import _temp._spring._injection._scheduleTaskHandler.AbstractTaskHandler;

/**
 * Created by fishsey on 2017/9/28.
 */
public class PrintHandler extends AbstractTaskHandler
{
    @Override
    public boolean doTask(String name)
    {
        System.out.println(name);
        return true;
    }

}
