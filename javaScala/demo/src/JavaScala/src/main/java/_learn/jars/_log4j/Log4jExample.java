package _learn.jars._log4j;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.sql.SQLException;

public class Log4jExample
{
    /* Get actual class name to be printed on */
    static Logger log = Logger.getLogger(Log4jExample.class.getName());

    public static void main(String[] args) throws IOException, SQLException
    {
		//默认读取的是项目根目录的路径
        PropertyConfigurator.configure("log4j.properties");

        log.warn("Hello this is an info message");
    }
}
