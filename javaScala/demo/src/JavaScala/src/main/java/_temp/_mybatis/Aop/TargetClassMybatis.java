package _temp._mybatis.Aop;

import _temp._mybatis.dao.PersonDao;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by fishsey on 2017/9/7.
 */
@Component
public class TargetClassMybatis
{
    @Autowired
    PersonDao personDao;

    @Test
    public void test_update() throws IOException
    {
        //File pf = new File("temp.txt");
        //pf.createNewFile();

        PropertyConfigurator.configure("tempData/log4j.properties");
        personDao.updateNameById("newNames", 1);
    }
}
