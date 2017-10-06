package com.fishsey.bookRecys.crawler.exec;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by root on 7/17/17.
 */
@Component
public class ExecShell
{
    public Process invokeShellByJVM(String cmd)
    {
        try
        {
            return Runtime.getRuntime().exec(cmd);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
