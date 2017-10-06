package _temp._mybatis.test;

import _temp._mybatis.dao.PersonGroupInfoDao;
import _temp._mybatis.entity.PersonGroupInfoEntity;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;

/**
 * Created by root on 7/18/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-mybatis.xml"})
public class _PersonGroupTest
{
    @Autowired
    PersonGroupInfoDao personGroupDao;


    @Test
    public void test_insert() throws FileNotFoundException
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        PersonGroupInfoEntity p1 = new PersonGroupInfoEntity();
        p1.setFlag(21);
        p1.setSchool("hdu");

        personGroupDao.insertPersnGroup(p1);

    }


}
