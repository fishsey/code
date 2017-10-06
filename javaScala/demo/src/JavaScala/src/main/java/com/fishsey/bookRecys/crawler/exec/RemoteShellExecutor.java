package com.fishsey.bookRecys.crawler.exec;

/**
 * Created by root on 7/19/17.
 */

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class RemoteShellExecutor
{
    private Connection conn;
    private String charset = Charset.defaultCharset().toString();

    private static String ip="10.65.7.171";
    private static String osUsername="root";
    private static String password="node1.123456";

    private static final int TIME_OUT = 1000 * 5 * 60;

    public RemoteShellExecutor()
    {
        this(ip, osUsername, password);
    }
    public RemoteShellExecutor(String ip, String usr, String pasword)
    {
        this.ip = ip;
        this.osUsername = usr;
        this.password = pasword;
    }

    private boolean login() throws IOException
    {
        conn = new Connection(ip);
        conn.connect();
        return conn.authenticateWithPassword(osUsername, password);
    }

    /**
     * 执行脚本
     * @param cmds
     * @return
     * @throws Exception
     */
    public int exec(String cmds) throws Exception
    {
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        int ret = -1;
        try
        {
            if (login())
            {
                Session session = conn.openSession();
                session.execCommand(cmds);

                stdOut = new StreamGobbler(session.getStdout());
                outStr = processStream(stdOut, charset);

                stdErr = new StreamGobbler(session.getStderr());
                outErr = processStream(stdErr, charset);

                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);

                System.out.println("outStr=" + outStr);
                System.out.println("outErr=" + outErr);

                ret = session.getExitStatus();
            } else
            {
                throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
            }
        } finally
        {
            if (conn != null)
            {
                conn.close();
            }
            IOUtils.closeQuietly(stdOut);
            IOUtils.closeQuietly(stdErr);
        }
        return ret;
    }

    private String processStream(InputStream in, String charset) throws Exception
    {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1)
        {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    public static void main(String args[]) throws Exception
    {
        RemoteShellExecutor executor = new RemoteShellExecutor("192.168.234.123", "root", "beebank");
        // 执行myTest.sh 参数为java Know dummy
        System.out.println(executor.exec("/home/IFileGenTool /load_data.sh t_users myDataBase01"));
    }
}
