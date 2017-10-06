package _temp._mybatis.test;

import _temp._mybatis.dao.PersonDao;
import _temp._mybatis.entity.PersonEntity;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Created by root on 7/18/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-mybatis.xml"})
public class _PersonTest
{
    @Autowired
    PersonDao personDao;

    @Test
    public void test_insert() throws FileNotFoundException
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        PersonEntity p1 = new PersonEntity();
        p1.setAge(11);
        p1.setName("11");
        p1.setFlag(11);

        personDao.insertPerson(p1);

        System.out.println(p1.getId());

    }

    @Test
    public void test_inserts() throws FileNotFoundException
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        PersonEntity p1 = new PersonEntity();
        p1.setAge(21);
        p1.setName("21");
        p1.setFlag(21);

        PersonEntity p2 = new PersonEntity();
        p2.setAge(22);
        p2.setName("22");
        p2.setFlag(22);

        PersonEntity p3 = new PersonEntity();
        p3.setAge(23);
        p3.setName("23");
        p3.setFlag(23);

        personDao.insertPersons(Arrays.asList(p1, p2, p3));

    }



}
