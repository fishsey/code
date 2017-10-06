package _learn.jars.nettyInAction.demos._udp;

import java.net.InetSocketAddress;

/**
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public final class LogEvent
{
    public static final byte SEPARATOR = (byte) ':';

    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(String logfile, String msg)
    {
        this(null, -1, logfile, msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logfile, String msg)
    {
        this.source = source;
        this.received = received;

        this.logfile = logfile;
        this.msg = msg;
    }

    public InetSocketAddress getSource()
    {
        return source;
    }

    public String getLogfile()
    {
        return logfile;
    }

    public String getMsg()
    {
        return msg;
    }

    public long getReceivedTimestamp()
    {
        return received;
    }
}
