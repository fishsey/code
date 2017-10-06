package _temp._mybatis.test;

import _temp._mybatis.dao.PersonDao;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by fishsey on 2017/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-mybatis.xml"})
public class _PrintPluigTest
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
